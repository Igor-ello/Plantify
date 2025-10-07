package com.example.myplants.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.MainFacade
import com.example.myplants.data.MainFacadeInterface
import com.example.myplants.domain.PlantDataInitializer
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import kotlinx.coroutines.launch

class MainViewModel(
    private val facade: MainFacadeInterface
) : ViewModel() {

    val plants: LiveData<List<PlantWithPhotos>> = facade.getAllPlantsWithPhotos()

    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            facade.updatePlant(plant)
        }
    }

    // Favourite
    val favorites: LiveData<List<PlantWithPhotos>> = facade.getFavorites()

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            facade.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    // Wishlist
    val wishlist: LiveData<List<PlantWithPhotos>> = facade.getWishlist()

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            facade.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }

    // Genus

    suspend fun getOrCreateGenusOnce(name: String): Genus {
        return facade.getGenusByName(name)
    }

    fun getOrCreateGenus(genusName: String): LiveData<Genus> {
        // Сначала пытаемся найти существующий род
        var genus = facade.getGenusByNameLive(genusName)

        // Если не найден, создаем новый
        if (genus == null) {
            val newGenus = Genus(
                id = 0L,
                main = MainInfo(genus = genusName, species = ""),
                care = CareInfo(),
                lifecycle = LifecycleInfo(),
                health = HealthInfo(),
                state = StateInfo()
            )
            viewModelScope.launch {
                val newId = facade.insertGenus(newGenus)
                genus = facade.getGenusByIdLive(newId)
            }
        }
        return genus
    }
}