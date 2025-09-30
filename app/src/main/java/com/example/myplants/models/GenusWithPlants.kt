package com.example.myplants.models

import androidx.room.Embedded
import androidx.room.Relation

data class GenusWithPlants(
    @Embedded val genus: Genus,
    @Relation(
        parentColumn = "id",
        entityColumn = "genus_id"
    )
    val plants: List<Plant>
)