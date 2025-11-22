package com.example.myplants.data.backup

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val plants: List<Plant>,
    val photos: List<PlantPhoto>,
    val genera: List<Genus>
)
