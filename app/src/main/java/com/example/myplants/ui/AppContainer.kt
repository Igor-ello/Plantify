package com.example.myplants.ui

import android.content.Context
import com.example.myplants.data.backup.BackupRepository
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.db.AppDatabase

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
