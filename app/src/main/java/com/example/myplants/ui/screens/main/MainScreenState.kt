package com.example.myplants.ui.screens.main

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.PlantWithPhotos

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