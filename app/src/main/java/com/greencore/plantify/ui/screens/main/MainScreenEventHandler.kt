package com.greencore.plantify.ui.screens.main

import com.greencore.plantify.core.data.local.relation.PlantWithPhotos

data class MainScreenEventHandler(
    val onAddPlant: () -> Unit = {},
    val onPlantClick: (Long) -> Unit = {},
    val onGenusClick: (Long) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onToggleGenusExpanded: (Long) -> Unit = {}
)