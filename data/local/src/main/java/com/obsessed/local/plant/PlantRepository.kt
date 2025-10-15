package com.obsessed.local.plant

import com.example.myplants.dao.PlantDao
import com.example.myplants.models.Plant

class PlantRepository(
    private val plantDao: PlantDao
) : PlantRepositoryInterface {

    // Работа с растениями
    override suspend fun insertPlant(plant: Plant) =
        plantDao.insertPlant(plant)

    override suspend fun insertPlants(plants: List<Plant>) =
        plantDao.insertPlants(plants)

    override suspend fun updatePlant(plant: Plant) =
        plantDao.updatePlant(plant)

    override suspend fun deleteAllPlants() =
        plantDao.deleteAllPlants()

    override suspend fun getAllPlants() =
        plantDao.getAllPlants()

    override suspend fun deletePlantById(plantId: Long) =
        plantDao.deletePlantById(plantId)

    override fun getAllPlantsLive() =
        plantDao.getAllPlantsLive()
}