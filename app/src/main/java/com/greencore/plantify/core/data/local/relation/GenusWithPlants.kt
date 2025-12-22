package com.greencore.plantify.core.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.Plant

data class GenusWithPlants(
    @Embedded val genus: Genus,
    @Relation(
        parentColumn = "id",
        entityColumn = "genus_id"
    )
    val plantsWithPhotos: List<Plant>
)