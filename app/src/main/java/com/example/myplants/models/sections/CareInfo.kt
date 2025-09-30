package com.example.myplants.models.sections

import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class CareInfo(
    var lighting: String? = null,

    var temperature: String? = null,

    @ColumnInfo(name = "air_humidity")
    var airHumidity: String? = null,

    @ColumnInfo(name = "soil_composition")
    var soilComposition: String? = null,

    var transfer: String? = null,

    @Embedded
    var watering: WateringInfo = WateringInfo(),

    @Embedded
    var fertilizer: FertilizerInfo = FertilizerInfo(),
)