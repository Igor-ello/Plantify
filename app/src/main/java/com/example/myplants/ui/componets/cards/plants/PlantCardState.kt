package com.example.myplants.ui.componets.cards.plants

import com.example.myplants.core.data.local.relation.PlantWithPhotos

data class PlantCardState(
    val plantWithPhotos: PlantWithPhotos,
    val editable: Boolean = false
) {
    val plant get() = plantWithPhotos.plant
    val mainPhotoBytes get() = plantWithPhotos.photos.firstOrNull()?.imageData
}