package com.example.myplants.ui

import android.content.Context
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.data.MainFacade
import com.example.myplants.data.backup.BackupRepository
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.db.AppDatabase
import com.example.myplants.data.genus.GenusRepository
import com.example.myplants.data.photo.PhotoRepository
import com.example.myplants.data.plant_with_photos.PlantStateRepository
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository

open class AppContainer(context: Context) : AppContainerInterface {
    private val database = AppDatabase.getInstance(context)

    override val mainFacade = MainFacade(
        plantRepository = PlantRepository(database.plantDao),
        plantWithPhotosRepository = PlantWithPhotosRepository(
            database.plantWithPhotosDao,
            database.plantDao,
            database.plantPhotoDao
        ),
        plantStateRepository = PlantStateRepository(database.plantState),
        genusRepository = GenusRepository(database.genusDao)
    )

    override val plantRepository = PlantRepository(
        plantDao = database.plantDao
    )

    override val photoRepository = PhotoRepository(
        photoDao = database.plantPhotoDao
    )

    override val backupRepository = BackupRepository(
        plantRepository = PlantRepository(database.plantDao),
        photoRepository = PhotoRepository(database.plantPhotoDao),
        genusRepository = GenusRepository(database.genusDao),
        context = context
    )

    override val plantWithPhotosRepository = PlantWithPhotosRepository(
        plantWithPhotosDao = database.plantWithPhotosDao,
        plantDao = database.plantDao,
        photoDao = database.plantPhotoDao
    )

    override val genusRepository = GenusRepository(
        genusDao = database.genusDao
    )
}
