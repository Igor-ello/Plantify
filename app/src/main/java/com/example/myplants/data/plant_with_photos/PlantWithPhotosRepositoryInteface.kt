package com.example.myplants.data.plant_with_photos

import com.example.myplants.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface PlantWithPhotosRepositoryInterface {
    fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?>
}