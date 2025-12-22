package com.greencore.plantify.data.backup

import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val plants: List<Plant>,
    val photos: List<PlantPhoto>,
    val genera: List<Genus>
)
