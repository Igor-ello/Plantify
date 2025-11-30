package com.example.myplants.data.plant

import androidx.lifecycle.LiveData
import com.example.myplants.core.data.local.entity.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepositoryInterface {

    suspend fun insertPlant(plant: Plant): Long

    suspend fun insertPlants(plants: List<Plant>)

    suspend fun updatePlant(plant: Plant)

    suspend fun deleteAllPlants()

    fun getAllPlants(): Flow<List<Plant>>

    suspend fun deletePlantById(plantId: Long)

    fun getAllPlantsLive(): LiveData<List<Plant>>
}
