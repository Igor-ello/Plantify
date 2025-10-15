package com.obsessed.domain.models

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
