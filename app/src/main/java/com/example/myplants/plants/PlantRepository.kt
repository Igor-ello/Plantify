package com.example.myplants.plants

import androidx.lifecycle.LiveData
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos

class PlantRepository(
    private val plantDao: PlantDao,
    private val photoDao: PlantPhotoDao
) {

    // Растения с их фотографиями
    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>> =
        plantDao.getAllWithPhotos()

    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantDao.getByIdWithPhotos(plantId)


    // Работа с растениями
    suspend fun insertPlant(plant: Plant): Long = plantDao.insert(plant)

    suspend fun updatePlant(plant: Plant) = plantDao.update(plant)

    suspend fun deletePlant(plant: Plant) = plantDao.delete(plant)

    suspend fun getAllPlantsSnapshot(): List<Plant> = plantDao.getAllSnapshot()

    // Работа с фотографиями
    suspend fun insertPhoto(photo: PlantPhoto) = photoDao.insertPhoto(photo)

    suspend fun insertPhotos(photos: List<PlantPhoto>) = photoDao.insertPhotos(photos)

    suspend fun deletePhoto(photo: PlantPhoto) = photoDao.deletePhoto(photo)

    suspend fun setMainPhoto(plantId: Long, photoId: Long) = photoDao.setMainPhoto(plantId, photoId)

    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto> = photoDao.getPhotosForPlant(plantId)
}

