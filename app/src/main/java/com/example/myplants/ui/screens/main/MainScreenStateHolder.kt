package com.example.myplants.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.data.main_facade.MainFacadeInterface
import com.example.myplants.domain.usecase.initialization.PlantDataInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenStateHolder @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    init {
        initializeTestDb()
        loadData()
    }

    private fun initializeTestDb () {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }
    }

    @OptIn(FlowPreview::class)
    private fun loadData() {
        viewModelScope.launch {
            combine(
                facade.getAllPlantsWithPhotos(),
                facade.getAllGenus()
                    .map { genuses -> genuses.associateBy { it.main.genus } }
            ) { plants, genusMap ->
                val currentState = _uiState.value
                if (
                    currentState.plants != plants ||
                    currentState.genusMap != genusMap
                ) {
                    _uiState.update {
                        it.copy(
                            plants = plants,
                            genusMap = genusMap,
                            isLoading = false
                        )
                    }
                }
            }.collect()
        }
    }

    fun toggleGenusExpansion(genusId: Long) {
        _uiState.update { it.toggleGenusExpansion(genusId) }
    }

    fun toggleFavorite(plantWithPhotos: PlantWithPhotos) {
        viewModelScope.launch {
            facade.setFavorite(
                plantWithPhotos.plant.id,
                !plantWithPhotos.plant.state.isFavorite
            )
        }
    }

    fun toggleWishlist(plantWithPhotos: PlantWithPhotos) {
        viewModelScope.launch {
            facade.setWishlist(
                plantWithPhotos.plant.id,
                !plantWithPhotos.plant.state.isWishlist
            )
        }
    }
}

data class MainScreenState(
    val plants: List<PlantWithPhotos> = emptyList(),
    val genusMap: Map<String, Genus> = emptyMap(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val expandedGenusIds: Set<Long> = emptySet()
) {
    val groupedPlants: Map<String, List<PlantWithPhotos>>
        get() = plants.groupBy { it.plant.main.genus }

    fun isGenusExpanded(genusId: Long): Boolean = expandedGenusIds.contains(genusId)

    fun toggleGenusExpansion(genusId: Long): MainScreenState {
        val newExpandedIds = if (expandedGenusIds.contains(genusId)) {
            expandedGenusIds - genusId
        } else {
            expandedGenusIds + genusId
        }
        return copy(expandedGenusIds = newExpandedIds)
    }
}
