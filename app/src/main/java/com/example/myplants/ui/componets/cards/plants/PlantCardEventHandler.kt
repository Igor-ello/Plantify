package com.example.myplants.ui.componets.cards.plants

import com.example.myplants.core.data.local.entity.PlantEntityInterface
import com.example.myplants.core.data.local.entity.PlantWithPhotos

data class PlantCardEventHandler(
    val onClick: (PlantWithPhotos) -> Unit = {},
    val onValueChange: (PlantEntityInterface) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {}
)