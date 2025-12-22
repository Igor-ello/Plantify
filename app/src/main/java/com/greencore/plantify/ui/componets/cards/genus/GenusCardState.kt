package com.greencore.plantify.ui.componets.cards.genus

import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos

data class GenusCardState(
    val genus: Genus,
    val plants: List<PlantWithPhotos>,
    val isExpanded: Boolean = false
)