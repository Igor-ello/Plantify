package com.example.myplants.ui.componets.common.cards.plants

import com.example.myplants.models.PlantEntityInterface
import com.example.myplants.models.PlantWithPhotos

data class PlantCardEventHandler(
    val onClick: (PlantWithPhotos) -> Unit = {},
    val onValueChange: (PlantEntityInterface) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {}
)