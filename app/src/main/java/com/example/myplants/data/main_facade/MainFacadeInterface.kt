package com.example.myplants.data.main_facade

import androidx.lifecycle.LiveData
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface MainFacadeInterface {

    // Plant

    suspend fun updatePlant(plant: Plant)

    suspend fun insertPlant(plant: Plant): Long

    suspend fun getAllPlants(): List<Plant>

    // State

    fun getFavorites(): Flow<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): Flow<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)

    // PlantWithPhotos

    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>

    // Genus

    suspend fun getGenusById(genusId: Long): Genus

    suspend fun insertGenus(genus: Genus): Long

    fun getGenusByIdLive(genusId: Long): LiveData<Genus>

    suspend fun getGenusByName(genusName: String): Genus

    fun getGenusByNameLive(genusName: String): LiveData<Genus>
}