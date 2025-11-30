package com.example.myplants.data.photo

import com.example.myplants.core.data.local.entity.PlantPhoto
import kotlinx.coroutines.flow.Flow

interface PhotoRepositoryInterface {

    suspend fun insertPhoto(photo: PlantPhoto)

    suspend fun insertPhotos(photos: List<PlantPhoto>)

    suspend fun updatePhoto(photo: PlantPhoto)

    suspend fun deletePhotoById(id: Long)

    suspend fun setMainPhoto(plantId: Long, photoId: Long)

    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto>

    suspend fun deleteAllPhoto()

    fun getAllPhoto(): Flow<List<PlantPhoto>>

    suspend fun deletePhotosByPlantId(plantId: Long)
}
