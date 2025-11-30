package com.example.myplants.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GenusWithPlants(
    @Embedded val genus: Genus,
    @Relation(
        parentColumn = "id",
        entityColumn = "genus_id"
    )
    val plantsWithPhotos: List<PlantWithPhotos>
)