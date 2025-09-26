package com.example.myplants.ui

import android.content.Context
import com.example.myplants.backup.BackupRepository
import com.example.myplants.db.AppDatabase
import com.example.myplants.plant.PlantRepository

class AppContainer(context: Context) {
    private val database = AppDatabase.getInstance(context)

    val plantRepository = PlantRepository(
        database.plantDao,
        database.plantPhotoDao
    )

    val backupRepository = BackupRepository(
        database.plantDao,
        database.plantPhotoDao,
        context
    )
}
