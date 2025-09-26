package com.example.myplants.backup

import java.io.File

class FakeBackupRepository : BackupRepositoryInterface {
    override suspend fun createBackup() = File("fake_backup.json")
    override fun listBackups() = listOf(File("fake_backup.json"))
    override suspend fun deleteBackupFile(file: File) {}
    override suspend fun restoreBackup(file: File) {}
    override suspend fun restoreMerge(file: File) {}
}
