package com.example.myplants.backup

import java.io.File

interface BackupRepositoryInterface {
    suspend fun createBackup(): File
    fun listBackups(): List<File>
    suspend fun deleteBackupFile(file: File)
    suspend fun restoreBackup(file: File)
    suspend fun restoreMerge(file: File)
}
