package com.example.myplants.models.sections

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class LifecycleInfo(
    var lifecycle: String? = null,

    var bloom: String? = null,

    var reproduction: String? = null,

    @ColumnInfo("first_landing")
    var firstLanding: String? = null,

    @ColumnInfo("is_toxic")
    var isToxic: Boolean? = null,

    @ColumnInfo("about_the_plant")
    var aboutThePlant: String? = null,
)