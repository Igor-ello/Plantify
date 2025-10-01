package com.example.myplants.ui

import android.content.Context
import com.example.myplants.data.backup.BackupRepository
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.db.AppDatabase

open class AppContainer(context: Context) : AppContainerInterface {
    private val database = AppDatabase.getInstance(context)

    override val plantRepository = PlantRepository(
        database.plantDao,
        database.plantPhotoDao
    )

    override val backupRepository = BackupRepository(
        database.plantDao,
        database.plantPhotoDao,
        database.genusDao,
        context
    )
}
