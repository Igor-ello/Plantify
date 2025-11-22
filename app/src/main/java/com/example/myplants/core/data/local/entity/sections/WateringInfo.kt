package com.example.myplants.core.data.local.entity.sections

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class WateringInfo(
    var watering: String? = null,

    @ColumnInfo(name = "watering_frequency_per_month")
    var frequencyPerMonth: Int? = null,

    @ColumnInfo(name = "watering_last_date")
    var lastDate: String? = null,

    @ColumnInfo(name = "watering_is_active")
    var isActive: Boolean = false
)