package com.example.myplants.ui.screens.main

import com.example.myplants.core.data.local.entity.PlantWithPhotos

data class MainScreenEventHandler(
    val onPlantClick: (PlantWithPhotos) -> Unit = {},
    val onAddPlant: () -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onNavigateToGenusDetail: (Long) -> Unit = {},
    val onToggleGenusExpanded: (Long) -> Unit = {}
)