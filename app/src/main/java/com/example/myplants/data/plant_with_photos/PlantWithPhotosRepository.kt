package com.example.myplants.data.plant_with_photos

import com.example.myplants.core.data.local.db.dao.PlantWithPhotosDao
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

class PlantWithPhotosRepository(
    private val plantWithPhotosDao: PlantWithPhotosDao
): PlantWithPhotosRepositoryInterface {
    override fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>> =
        plantWithPhotosDao.getAllPlantsWithPhotos()

    override fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?> =
        plantWithPhotosDao.getPlantWithPhotosById(plantId)
}