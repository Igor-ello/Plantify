package com.example.myplants.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.data.photo.PhotoRepository
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository

class PlantDetailViewModelFactory(
    private val plantId: Long,
    private val plantWithPhotosRepository: PlantWithPhotosRepository,
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)) {
            return PlantDetailViewModel(
                plantWithPhotosRepository, plantRepository, photoRepository, plantId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

