package com.example.myplants.ui

import com.example.myplants.data.MainFacadeInterface
import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.genus.GenusRepository
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository
import com.example.myplants.models.PlantPhoto

interface AppContainerInterface {
    val mainFacade: MainFacadeInterface
    val plantRepository: PlantRepositoryInterface
    val photoRepository: PhotoRepositoryInterface
    val backupRepository: BackupRepositoryInterface
    val plantWithPhotosRepository: PlantWithPhotosRepository
    val genusRepository: GenusRepository
}