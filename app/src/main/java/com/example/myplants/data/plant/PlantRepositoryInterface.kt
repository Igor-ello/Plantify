package com.example.myplants.data.plant

import androidx.lifecycle.LiveData
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos

interface PlantRepositoryInterface {
    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>>
    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>

    suspend fun insertPlant(plant: Plant): Long
    suspend fun updatePlant(plant: Plant)
    suspend fun getAllPlantsSnapshot(): List<Plant>

    fun getFavorites(): LiveData<List<PlantWithPhotos>>
    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): LiveData<List<PlantWithPhotos>>
    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)

    suspend fun insertPhoto(photo: PlantPhoto)
    suspend fun insertPhotos(photos: List<PlantPhoto>)
    suspend fun updatePhoto(photo: PlantPhoto)
    suspend fun deletePhoto(photoId: Long)
    suspend fun setMainPhoto(plantId: Long, photoId: Long)
    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto>

    suspend fun deletePlantWithPhotos(plantId: Long)
}
