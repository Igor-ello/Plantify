package com.greencore.plantify.data.backup

import android.content.Context
import androidx.room.withTransaction
import com.greencore.plantify.core.data.local.db.AppDatabase
import com.greencore.plantify.data.genus.GenusRepositoryInterface
import com.greencore.plantify.data.photo.PhotoRepositoryInterface
import com.greencore.plantify.data.plant.PlantRepositoryInterface
import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto
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
            photoRepository.deleteAllPhoto()
            plantRepository.deleteAllPlants()
            genusRepository.deleteAllGenus()
            if (backup.genera.isNotEmpty()) genusRepository.insertGenera(backup.genera)
            if (backup.plants.isNotEmpty()) plantRepository.insertPlants(backup.plants)
            if (backup.photos.isNotEmpty()) photoRepository.insertPhotos(backup.photos)
        }
    }

    override suspend fun restoreMerge(file: File) = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext
        val json = file.readText()
        val backup: BackupData = Json.decodeFromString(json)
        if (backup.photos.isNotEmpty()) genusRepository.insertGenera(backup.genera)
        if (backup.plants.isNotEmpty()) plantRepository.insertPlants(backup.plants)
        if (backup.photos.isNotEmpty()) photoRepository.insertPhotos(backup.photos)
    }
}
