package com.example.myplants.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.data.main_facade.MainFacadeInterface
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
        loadData()
    }

    @OptIn(FlowPreview::class)
    private fun loadData() {
        viewModelScope.launch {
            // Оптимизация: используем distinctUntilChanged и debounce для уменьшения обновлений
            combine(
                facade.getAllPlantsWithPhotos()
                    .distinctUntilChanged()
                    .debounce(50),
                facade.getAllGenus()
                    .map { genuses -> genuses.associateBy { it.main.genus } }
                    .distinctUntilChanged()
                    .debounce(50)
            ) { plants, genusMap ->
                // Оптимизация: обновляем только если данные действительно изменились
                val currentState = _uiState.value
                if (currentState.plants != plants || currentState.genusMap != genusMap) {
                    _uiState.update { it.copy(
                        plants = plants,
                        genusMap = genusMap,
                        isLoading = false
                    ) }
                }
            }.collect()
        }
    }

    // Оптимизация: кэшируем вычисление groupedPlants
    private val cachedGroupedPlants = _uiState
        .map { it.groupedPlants }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

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