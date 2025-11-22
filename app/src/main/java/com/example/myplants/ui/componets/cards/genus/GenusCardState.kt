package com.example.myplants.ui.componets.cards.genus

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.PlantWithPhotos

data class GenusCardState(
    val genus: Genus,
    val plants: List<PlantWithPhotos>,
    val isExpanded: Boolean = false
)