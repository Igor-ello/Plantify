package com.example.myplants.backup

import android.content.Context
import androidx.room.withTransaction
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.db.AppDatabase
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
    private val plantDao: PlantDao,
    private val plantPhotoDao: PlantPhotoDao,
    private val context: Context
) : BackupRepositoryInterface {
    private val backupDir: File = File(context.filesDir, "backups").apply { mkdirs() }

    private fun generateBackupFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        return File(backupDir, "plants_backup_$timestamp.json")
    }

    override suspend fun createBackup(): File = withContext(Dispatchers.IO) {
        val plants: List<Plant> = plantDao.getAll()
        val photos: List<PlantPhoto> = plantPhotoDao.getAll()
        val backup = BackupData(plants = plants, photos = photos)
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
            plantPhotoDao.deleteAll()
            plantDao.deleteAll()
            if (backup.plants.isNotEmpty()) plantDao.insertAll(backup.plants)
            if (backup.photos.isNotEmpty()) plantPhotoDao.insertPhotos(backup.photos)
        }
    }

    override suspend fun restoreMerge(file: File) = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext
        val json = file.readText()
        val backup: BackupData = Json.decodeFromString(json)
        if (backup.plants.isNotEmpty()) plantDao.insertAll(backup.plants)
        if (backup.photos.isNotEmpty()) plantPhotoDao.insertPhotos(backup.photos)
    }
}
