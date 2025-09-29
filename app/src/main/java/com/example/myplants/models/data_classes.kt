package com.example.myplants.models

import kotlinx.serialization.Serializable

@Serializable
data class StateInfo (
    var isFavorite: Boolean = false,
    var isWishlist: Boolean = false,
    var isHideEmptyContent: Boolean = false
)

@Serializable
data class HealthInfo(
    var pests: String? = null,
    var diseases: String? = null
)

@Serializable
data class MainInfo(
    var name: String,
    var species: String,
    var subSpecies: String? = null
)