package com.example.myplants.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.plant.PlantRepositoryInterface
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

class PlantsViewModel(
    private val repository: PlantRepositoryInterface
) : ViewModel() {

    val plants: LiveData<List<PlantWithPhotos>> = repository.getAllPlantsWithPhotos()

    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(repository)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            repository.updatePlant(plant)
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

    suspend fun getGenusByName(name: String): Genus? {
        return repository.getGenusByName(name)
    }

    suspend fun getOrCreateGenus(genusName: String): Genus {
        // Сначала пытаемся найти существующий род
        var genus = repository.getGenusByName(genusName)

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
            val newId = repository.createGenus(newGenus)
            genus = repository.getGenusById(newId)
            Log.i("MyLog", "Created new genus: $genusName with ID: $newId")
        } else {
            Log.i("MyLog", "Found existing genus: $genusName with ID: ${genus.id}")
        }

        return genus
    }
}