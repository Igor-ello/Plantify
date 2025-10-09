package com.example.myplants.data.plant

import androidx.lifecycle.LiveData
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos

interface PlantRepositoryInterface {

    suspend fun insertPlant(plant: Plant): Long

    suspend fun insertPlants(plants: List<Plant>)

    suspend fun updatePlant(plant: Plant)

    suspend fun deleteAllPlants()

    suspend fun getAllPlants(): List<Plant>

    suspend fun deletePlantById(plantId: Long)

    fun getAllPlantsLive(): LiveData<List<Plant>>
}
