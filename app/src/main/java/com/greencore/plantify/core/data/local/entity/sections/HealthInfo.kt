package com.greencore.plantify.core.data.local.entity.sections

import kotlinx.serialization.Serializable

@Serializable
data class HealthInfo(
    var pests: String? = null,

    var diseases: String? = null
)