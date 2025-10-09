package com.example.myplants.data.main_facade

import androidx.lifecycle.LiveData
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos

interface MainFacadeInterface {

    // Plant

    suspend fun updatePlant(plant: Plant)

    suspend fun insertPlant(plant: Plant): Long

    suspend fun getAllPlants(): List<Plant>

    // State

    fun getFavorites(): LiveData<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): LiveData<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)

    // PlantWithPhotos

    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>

    // Genus

    suspend fun insertGenus(genus: Genus): Long

    fun getGenusByIdLive(genusId: Long): LiveData<Genus>

    suspend fun getGenusByName(genusName: String): Genus

    fun getGenusByNameLive(genusName: String): LiveData<Genus>
}