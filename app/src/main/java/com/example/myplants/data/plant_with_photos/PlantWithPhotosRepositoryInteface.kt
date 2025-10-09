package com.example.myplants.data.plant_with_photos

import androidx.lifecycle.LiveData
import com.example.myplants.models.PlantWithPhotos

interface PlantWithPhotosRepositoryInterface {
    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>>

    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>
}