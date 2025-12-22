package com.greencore.plantify.ui.componets.cards.genus

import com.greencore.plantify.core.data.local.relation.PlantWithPhotos

data class GenusCardEventHandler(
    val onPlantClick: (Long) -> Unit = {},
    val onGenusClick: (Long) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
    val onToggleExpanded: () -> Unit = {}
)