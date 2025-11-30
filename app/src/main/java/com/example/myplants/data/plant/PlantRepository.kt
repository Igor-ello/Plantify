package com.example.myplants.data.plant

import com.example.myplants.core.data.local.db.dao.PlantDao
import com.example.myplants.core.data.local.entity.Plant
import kotlinx.coroutines.flow.Flow

class PlantRepository(
    private val plantDao: PlantDao
) : PlantRepositoryInterface {

    override suspend fun insertPlant(plant: Plant) =
        plantDao.insertPlant(plant)

    override suspend fun insertPlants(plants: List<Plant>) =
        plantDao.insertPlants(plants)

    override suspend fun updatePlant(plant: Plant) =
        plantDao.updatePlant(plant)

    override suspend fun getPlantById(plantId: Long): Plant? =
        plantDao.getPlantById(plantId)

    override fun getPlantByIdLive(plantId: Long): Flow<Plant?> =
        plantDao.getPlantByIdLive(plantId)

    override suspend fun getAllPlants(): List<Plant> =
        plantDao.getAllPlants()

    override fun getAllPlantsLive(): Flow<List<Plant>> =
        plantDao.getAllPlantsLive()

    override suspend fun deleteAllPlants() =
        plantDao.deleteAllPlants()

    override suspend fun deletePlantById(plantId: Long) =
        plantDao.deletePlantById(plantId)
}