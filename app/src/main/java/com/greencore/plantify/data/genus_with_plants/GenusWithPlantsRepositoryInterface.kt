package com.greencore.plantify.data.genus_with_plants

import com.greencore.plantify.core.data.local.relation.GenusWithPlants
import kotlinx.coroutines.flow.Flow

interface GenusWithPlantsRepositoryInterface {

    fun getGenusWithPlantsById(genusId: Long): Flow<List<GenusWithPlants>>

    fun getAllGenusWithPlants(): Flow<List<GenusWithPlants>>
}