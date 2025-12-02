package com.example.myplants.data.genus_with_plants

import com.example.myplants.core.data.local.db.dao.GenusWithPlantsDao
import com.example.myplants.core.data.local.relation.GenusWithPlants
import kotlinx.coroutines.flow.Flow

class GenusWithPlantsRepository(
    private val genusWithPlantsDao: GenusWithPlantsDao
): GenusWithPlantsRepositoryInterface {

    override fun getGenusWithPlantsById(genusId: Long): Flow<List<GenusWithPlants>>  =
        genusWithPlantsDao.getGenusWithPlantsById(genusId)

    override fun getAllGenusWithPlants(): Flow<List<GenusWithPlants>> =
        genusWithPlantsDao.getAllGenusWithPlants()
}