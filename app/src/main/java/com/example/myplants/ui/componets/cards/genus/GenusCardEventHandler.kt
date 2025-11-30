package com.example.myplants.ui.componets.cards.genus

import com.example.myplants.core.data.local.relation.PlantWithPhotos

data class GenusCardEventHandler(
    val onPlantClick: (Long) -> Unit = {},
    val onGenusClick: (Long) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onToggleExpanded: () -> Unit = {}
)