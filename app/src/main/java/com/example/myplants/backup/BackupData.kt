package com.example.myplants.backup

import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val plants: List<Plant>,
    val photos: List<PlantPhoto>
)
