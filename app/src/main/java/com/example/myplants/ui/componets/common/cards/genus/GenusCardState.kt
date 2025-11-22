package com.example.myplants.ui.componets.common.cards.genus

import com.example.myplants.models.Genus
import com.example.myplants.models.PlantWithPhotos

data class GenusCardState(
    val genus: Genus,
    val plantWithPhotos: List<PlantWithPhotos>,
    val isExpanded: Boolean = false
)