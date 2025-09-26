package com.example.myplants.plant

import androidx.lifecycle.MutableLiveData
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos

class FakePlantRepository(
    private val fakePlants: List<PlantWithPhotos> = emptyList()
) : PlantRepositoryInterface {
    override fun getAllPlantsWithPhotos() = MutableLiveData(fakePlants)
    override fun getPlantWithPhotosById(plantId: Long) =
        MutableLiveData(fakePlants.find { it.plant.id == plantId })

    override suspend fun insertPlant(plant: Plant) = 1L
    override suspend fun updatePlant(plant: Plant) {}
    override suspend fun getAllPlantsSnapshot() = fakePlants.map { it.plant }

    override fun getFavorites() = MutableLiveData(fakePlants.filter { it.plant.isFavorite })
    override suspend fun setFavorite(plantId: Long, isFavorite: Boolean) {}

    override fun getWishlist() = MutableLiveData(fakePlants.filter { it.plant.isWishlist })
    override suspend fun setWishlist(plantId: Long, isWishlist: Boolean) {}

    override suspend fun insertPhoto(photo: PlantPhoto) {}
    override suspend fun insertPhotos(photos: List<PlantPhoto>) {}
    override suspend fun updatePhoto(photo: PlantPhoto) {}
    override suspend fun deletePhoto(photoId: Long) {}
    override suspend fun setMainPhoto(plantId: Long, photoId: Long) {}
    override suspend fun getPhotosForPlant(plantId: Long) = emptyList<PlantPhoto>()

    override suspend fun deletePlantWithPhotos(plantId: Long) {}
}
