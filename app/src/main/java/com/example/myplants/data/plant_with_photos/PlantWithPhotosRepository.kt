package com.example.myplants.data.plant_with_photos

import androidx.lifecycle.LiveData
import com.example.myplants.dao.PlantWithPhotosDao
import com.example.myplants.models.PlantWithPhotos

class PlantWithPhotosRepository(
    private val plantWithPhotosDao: PlantWithPhotosDao
): PlantWithPhotosRepositoryInterface {
    override fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>> =
        plantWithPhotosDao.getAllPlantsWithPhotos()

    override fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantWithPhotosDao.getPlantWithPhotosById(plantId)
}