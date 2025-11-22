package com.example.myplants.ui.componets.common.cards.plants

import com.example.myplants.models.PlantWithPhotos

data class PlantCardState(
    val plantWithPhotos: PlantWithPhotos,
    val editable: Boolean = false
) {
    val plant get() = plantWithPhotos.plant
    val mainPhotoUri get() = plantWithPhotos.photos.firstOrNull { it.isPrimary }?.uri
}