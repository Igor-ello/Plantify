package com.example.myplants.ui.screens.plant.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.state.PlantStateRepositoryInterface
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantStateViewModel @Inject constructor(
    private val repository: PlantStateRepositoryInterface
) : ViewModel() {

    private val _listType = MutableStateFlow<PlantStateType>(PlantStateType.FAVORITES)
    private val _uiState = MutableStateFlow<PlantListUiState>(PlantListUiState.Loading)

    val uiState: StateFlow<PlantListUiState> = _uiState.asStateFlow()

    init {
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            if (_uiState.value is PlantListUiState.Success) {
                _uiState.value = PlantListUiState.Loading
            }

            try {
                _listType
                    .collectLatest { listType ->
                        val plantsFlow = when (listType) {
                            PlantStateType.FAVORITES -> repository.getFavorites()
                            PlantStateType.WISHLIST -> repository.getWishlist()
                        }

                        plantsFlow.collect { plants ->
                            _uiState.value = PlantListUiState.Success(plants)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = PlantListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setListType(type: PlantStateType) {
        if (_listType.value != type) {
            _listType.value = type
        }
    }

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            repository.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            repository.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }

    fun retry() {
        loadPlants()
    }
}

enum class PlantStateType {
    FAVORITES, WISHLIST
}

sealed interface PlantListUiState {
    object Loading : PlantListUiState
    data class Success(val plants: List<PlantWithPhotos>) : PlantListUiState
    data class Error(val message: String) : PlantListUiState
}
