package com.example.myplants.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlantWithPhotos(
    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "id",
        entityColumn = "plant_id"
    )
    val photos: List<PlantPhoto>
)
