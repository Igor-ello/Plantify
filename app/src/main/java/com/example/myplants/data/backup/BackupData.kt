package com.example.myplants.data.backup

import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val plants: List<Plant>,
    val photos: List<PlantPhoto>,
    val genera: List<Genus>
)
