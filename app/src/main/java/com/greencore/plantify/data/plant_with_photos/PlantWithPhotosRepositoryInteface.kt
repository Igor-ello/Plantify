package com.greencore.plantify.data.plant_with_photos

import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface PlantWithPhotosRepositoryInterface {
    fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?>
}