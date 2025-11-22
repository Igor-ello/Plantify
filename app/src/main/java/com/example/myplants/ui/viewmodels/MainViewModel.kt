package com.example.myplants.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.main_facade.MainFacadeInterface
import com.example.myplants.domain.PlantDataInitializer
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {

    private val _genusMap = MutableLiveData<Map<String, Genus>>(emptyMap())
    val genusMap: LiveData<Map<String, Genus>> = _genusMap

    val plants: LiveData<List<PlantWithPhotos>> = facade.getAllPlantsWithPhotos()

    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }
    }

    // Favourite
    val favorites: Flow<List<PlantWithPhotos>> = facade.getFavorites()

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            facade.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    // Wishlist
    val wishlist: Flow<List<PlantWithPhotos>> = facade.getWishlist()

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            facade.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }

    // Genus
    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }

        // Наблюдаем за растениями и когда они загружаются - загружаем роды
        viewModelScope.launch {
            plants.asFlow().collect { plantsList ->
                if (plantsList.isNotEmpty()) {
                    loadAllGenuses(plantsList)
                }
            }
        }
    }

    private suspend fun loadAllGenuses(plantsList: List<PlantWithPhotos>) {
        val genusNames = plantsList.map { it.plant.main.genus }.distinct()
        val newGenusMap = mutableMapOf<String, Genus>()

        genusNames.forEach { genusName ->
            var genus = facade.getGenusByName(genusName)

            if (genus == null) {
                val newGenus = Genus(
                    id = 0L,
                    main = MainInfo(genus = genusName, species = ""),
                    care = CareInfo(),
                    lifecycle = LifecycleInfo(),
                    health = HealthInfo(),
                    state = StateInfo()
                )
                val newId = facade.insertGenus(newGenus)
                genus = facade.getGenusById(newId)
            }

            if (genus != null) {
                newGenusMap[genusName] = genus
            }
        }

        _genusMap.value = newGenusMap
    }
}