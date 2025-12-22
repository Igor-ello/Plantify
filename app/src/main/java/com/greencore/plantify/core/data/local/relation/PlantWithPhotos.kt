package com.greencore.plantify.core.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto

data class PlantWithPhotos(
    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "id",
        entityColumn = "plant_id"
    )
    val photos: List<PlantPhoto>
)
