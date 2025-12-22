package com.greencore.plantify.ui.componets.cards.plants

import com.greencore.plantify.core.data.local.relation.PlantWithPhotos

data class PlantCardState(
    val plantWithPhotos: PlantWithPhotos,
    val editable: Boolean = false
) {
    val plant get() = plantWithPhotos.plant
    val photos get() = plantWithPhotos.photos
    val mainPhotoBytes get() = plantWithPhotos.photos.firstOrNull()?.imageData
}