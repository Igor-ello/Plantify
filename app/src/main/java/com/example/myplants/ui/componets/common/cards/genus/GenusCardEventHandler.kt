package com.example.myplants.ui.componets.common.cards.genus

import com.example.myplants.models.PlantWithPhotos

data class GenusCardEventHandler(
    val onPlantClick: (PlantWithPhotos) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onNavigateToGenusDetail: (Long) -> Unit = {},
    val onToggleExpanded: () -> Unit = {}
)