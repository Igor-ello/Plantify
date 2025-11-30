package com.example.myplants.ui.screens.main

import com.example.myplants.core.data.local.entity.PlantWithPhotos

data class MainScreenEventHandler(
    val onAddPlant: () -> Unit = {},
    val onPlantClick: (Long) -> Unit = {},
    val onGenusClick: (Long) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onToggleGenusExpanded: (Long) -> Unit = {}
)