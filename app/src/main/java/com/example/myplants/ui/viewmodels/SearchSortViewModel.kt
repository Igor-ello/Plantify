package com.example.myplants.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myplants.models.PlantWithPhotos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchSortViewModel @Inject constructor() : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _sortType = MutableStateFlow(SortType.BY_NAME)
    val sortType: StateFlow<SortType> = _sortType

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun setSortType(type: SortType) {
        _sortType.value = type
    }

    // Применяем фильтр и сортировку к списку растений
    fun apply(plants: List<PlantWithPhotos>): List<PlantWithPhotos> {
        return plants
            .filter { it.plant.main.species.contains(_query.value, ignoreCase = true) }
            .sortedWith(
                when (_sortType.value) {
                    SortType.BY_NAME -> compareBy { it.plant.main.species }
                    SortType.BY_GENUS -> compareBy { it.plant.main.genus }
                    SortType.BY_FAVORITE -> compareByDescending { it.plant.state.isFavorite }
                }
            )
    }
}

enum class SortType {
    BY_NAME, BY_GENUS, BY_FAVORITE
}
