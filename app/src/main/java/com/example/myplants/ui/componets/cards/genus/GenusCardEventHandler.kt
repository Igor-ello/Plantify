package com.example.myplants.ui.componets.cards.genus

import com.example.myplants.core.data.local.entity.PlantWithPhotos

data class GenusCardEventHandler(
    val onPlantClick: (PlantWithPhotos) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onNavigateToGenusDetail: (Long) -> Unit = {},
    val onToggleExpanded: () -> Unit = {}
)