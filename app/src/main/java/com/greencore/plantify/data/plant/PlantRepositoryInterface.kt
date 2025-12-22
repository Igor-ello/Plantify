package com.greencore.plantify.data.plant

import com.greencore.plantify.core.data.local.entity.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepositoryInterface {

    suspend fun insertPlant(plant: Plant): Long

    suspend fun insertPlants(plants: List<Plant>)

    suspend fun updatePlant(plant: Plant)

    suspend fun getPlantById(plantId: Long): Plant?

    fun getPlantByIdLive(plantId: Long): Flow<Plant?>

    suspend fun getAllPlants(): List<Plant>

    fun getAllPlantsLive(): Flow<List<Plant>>

    suspend fun deleteAllPlants()

    suspend fun deletePlantById(plantId: Long)
}
