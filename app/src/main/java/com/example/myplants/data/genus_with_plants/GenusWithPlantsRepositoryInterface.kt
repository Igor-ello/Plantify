package com.example.myplants.data.genus_with_plants

import com.example.myplants.core.data.local.relation.GenusWithPlants
import kotlinx.coroutines.flow.Flow

interface GenusWithPlantsRepositoryInterface {

    fun getGenusWithPlantsById(genusId: Long): Flow<List<GenusWithPlants>>

    fun getAllGenusWithPlants(): Flow<List<GenusWithPlants>>
}