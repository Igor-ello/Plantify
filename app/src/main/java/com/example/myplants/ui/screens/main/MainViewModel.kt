package com.example.myplants.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.data.main_facade.MainFacadeInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {

    val plants: Flow<List<PlantWithPhotos>> = facade.getAllPlantsWithPhotos()
    val genusMap: Flow<Map<String, Genus>> = facade.getAllGenus().map { genusList ->
        genusList.associateBy { it.main.genus }
    }

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            facade.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            facade.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }
}