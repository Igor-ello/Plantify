package com.example.myplants.data.photo

import com.example.myplants.models.PlantPhoto

interface PhotoRepositoryInterface {

    suspend fun insertPhoto(photo: PlantPhoto)

    suspend fun insertPhotos(photos: List<PlantPhoto>)

    suspend fun updatePhoto(photo: PlantPhoto)

    suspend fun deletePhotoById(id: Long)

    suspend fun setMainPhoto(plantId: Long, photoId: Long)

    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto>

    suspend fun deleteAllPhoto()

    suspend fun getAllPhoto(): List<PlantPhoto>

    suspend fun deletePhotosByPlantId(plantId: Long)
}
