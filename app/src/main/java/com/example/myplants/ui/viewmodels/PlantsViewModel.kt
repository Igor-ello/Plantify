package com.example.myplants.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.domain.PlantDataInitializer
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import kotlinx.coroutines.launch

class PlantsViewModel(
    private val repository: PlantRepositoryInterface
) : ViewModel() {

    val plants: LiveData<List<PlantWithPhotos>> = repository.getAllPlantsWithPhotos()


    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(repository)
        }
    }

    // Favourite
    val favorites: LiveData<List<PlantWithPhotos>> = repository.getFavorites()

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            repository.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    // Wishlist
    val wishlist: LiveData<List<PlantWithPhotos>> = repository.getWishlist()

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            repository.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }
}