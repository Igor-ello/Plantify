package com.example.myplants.data.main_facade

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface MainFacadeInterface {

    // Plant

    suspend fun updatePlant(plant: Plant)

    suspend fun insertPlant(plant: Plant): Long

    suspend fun getAllPlants(): List<Plant>

    // Photo

    suspend fun insertPhoto(photo: PlantPhoto): Long

    // State

    fun getFavorites(): Flow<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): Flow<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)

    // PlantWithPhotos

    fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?>

    // Genus

    suspend fun insertGenus(genus: Genus): Long

    suspend fun getGenusById(genusId: Long): Genus?

    fun getGenusByIdLive(genusId: Long): Flow<Genus?>

    suspend fun getGenusByName(genusName: String): Genus?

    fun getGenusByNameLive(genusName: String): Flow<Genus?>

    suspend fun getAllGenus(): List<Genus>

    fun getAllGenusLive(): Flow<List<Genus>>
}