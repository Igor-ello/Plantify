package com.example.myplants.models.sections

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class FertilizerInfo(
    var fertilizer: String? = null,

    @ColumnInfo(name = "fertilizer_frequency_per_month")
    var frequencyPerMonth: Int? = null,

    @ColumnInfo(name = "fertilizer_last_date")
    var lastDate: String? = null,

    @ColumnInfo(name = "fertilizer_is_active")
    var isActive: Boolean = false
)