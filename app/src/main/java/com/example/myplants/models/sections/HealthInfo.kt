package com.example.myplants.models.sections

import kotlinx.serialization.Serializable

@Serializable
data class HealthInfo(
    var pests: String? = null,

    var diseases: String? = null
)