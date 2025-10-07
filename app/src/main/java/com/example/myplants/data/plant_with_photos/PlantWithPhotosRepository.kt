package com.example.myplants.data.plant_with_photos

import androidx.lifecycle.LiveData
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantWithPhotosDao
import com.example.myplants.models.PlantWithPhotos

class PlantWithPhotosRepository(
    private val plantWithPhotosDao: PlantWithPhotosDao,
    private val plantDao: PlantDao,
    private val photoDao: PlantPhotoDao
) {
    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>> =
        plantWithPhotosDao.getAllPlantsWithPhotos()

    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantWithPhotosDao.getPlantWithPhotosById(plantId)

    suspend fun deletePlantWithPhotos(plantId: Long) {
        photoDao.deletePhotosByPlantId(plantId)
        plantDao.deletePlantsById(plantId)
    }
}