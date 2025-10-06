package com.example.myplants.ui

import android.content.Context
import com.example.myplants.data.backup.BackupRepository
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.db.AppDatabase
import com.example.myplants.ui.viewmodels.GenusRepository

open class AppContainer(context: Context) : AppContainerInterface {
    private val database = AppDatabase.getInstance(context)

    override val plantRepository = PlantRepository(
        plantDao = database.plantDao,
        plantPhotoDao = database.plantPhotoDao,
        genusDao = database.genusDao
    )

    override val backupRepository = BackupRepository(
        plantDao = database.plantDao,
        plantPhotoDao = database.plantPhotoDao,
        database.genusDao,
        context = context
    )

    override val genusRepository = GenusRepository(
        genusDao = database.genusDao
    )
}
