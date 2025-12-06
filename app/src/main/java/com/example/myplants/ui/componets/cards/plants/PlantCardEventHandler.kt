package com.example.myplants.ui.componets.cards.plants

import android.graphics.Bitmap
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantEntityInterface
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.relation.PlantWithPhotos

data class PlantCardEventHandler(
    val onClick: (PlantWithPhotos) -> Unit = {},
    val onValueChange: (PlantEntityInterface) -> Unit = {},
    val onPhotosChanged: (List<PlantPhoto>) -> Unit = {},
    val onAddPhoto: (Bitmap) -> Unit = {},
    val onReplacePhoto: (Int, Bitmap) -> Unit = {_, _ ->},
    val onDeletePhoto: (Int) -> Unit = {},
    val onToggleFavorite: (PlantWithPhotos) -> Unit = {},
    val onToggleWishlist: (PlantWithPhotos) -> Unit = {},
)