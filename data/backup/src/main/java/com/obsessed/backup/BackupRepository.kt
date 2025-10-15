package com.obsessed.backup

import android.content.Context
import androidx.room.withTransaction
import com.example.myplants.core.db.AppDatabase
import com.example.myplants.data.genus.GenusRepositoryInterface
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BackupRepository(
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    private val genusRepository: GenusRepositoryInterface,
    private val context: Context
) : BackupRepositoryInterface {
    private val backupDir: File = File(context.filesDir, "backups").apply { mkdirs() }

    private fun generateBackupFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        return File(backupDir, "plants_backup_$timestamp.json")
    }

    override suspend fun createBackup(): File = withContext(Dispatchers.IO) {
        val plants: List<Plant> = plantRepository.getAllPlants()
        val photos: List<PlantPhoto> = photoRepository.getAllPhoto()
        val genera: List<Genus> = genusRepository.getAllGenus()
        val backup = BackupData(plants = plants, photos = photos, genera = genera)
        val json = Json { prettyPrint = true }.encodeToString(backup)
        val file = generateBackupFile()
        file.writeText(json)
        file
    }

    override fun listBackups(): List<File> =
        backupDir.listFiles()?.sortedByDescending { it.lastModified() } ?: emptyList()

    override suspend fun deleteBackupFile(file: File) = withContext(Dispatchers.IO) {
        if (file.exists()) file.delete()
    }

    override suspend fun restoreBackup(file: File) = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext
        val json = file.readText()
        val backup: BackupData = Json.decodeFromString(json)
        AppDatabase.getInstance(context).withTransaction {
            plantRepository.deleteAllPlants()
            photoRepository.deleteAllPhoto()
            genusRepository.deleteAllGenus()
            if (backup.plants.isNotEmpty()) plantRepository.insertPlants(backup.plants)
            if (backup.photos.isNotEmpty()) photoRepository.insertPhotos(backup.photos)
            if (backup.genera.isNotEmpty()) genusRepository.insertGenera(backup.genera)
        }
    }

    override suspend fun restoreMerge(file: File) = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext
        val json = file.readText()
        val backup: BackupData = Json.decodeFromString(json)
        if (backup.plants.isNotEmpty()) plantRepository.insertPlants(backup.plants)
        if (backup.photos.isNotEmpty()) photoRepository.insertPhotos(backup.photos)
        if (backup.photos.isNotEmpty()) genusRepository.insertGenera(backup.genera)
    }
}
