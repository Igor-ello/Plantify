package com.example.myplants.backup

import android.content.Context
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BackupRepository(
    private val plantDao: PlantDao,
    private val plantPhotoDao: PlantPhotoDao,
    context: Context
) {
    private val backupDir = File(context.filesDir, "backups").apply { mkdirs() }

    private fun generateBackupFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        return File(backupDir, "plants_backup_$timestamp.json")
    }

    suspend fun createBackup() {
        val plants = plantDao.getAll()
        val photos = plantPhotoDao.getAll()
        val backup = BackupData(plants, photos)

        val json = Json.encodeToString(backup)
        val file = generateBackupFile()
        file.writeText(json)
    }

    fun listBackups(): List<File> {
        return backupDir.listFiles()?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    suspend fun restoreBackup(file: File) {
        if (!file.exists()) return
        val json = file.readText()
        val backup = Json.decodeFromString<BackupData>(json)

        plantDao.insertAll(backup.plants)
        plantPhotoDao.insertAll(backup.photos)
    }
}