package com.greencore.plantify.core.data.local.entity.sections

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class StateInfo (
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "is_wishlist")
    var isWishlist: Boolean = false,

    @ColumnInfo(name = "is_hide_empty")
    var isHideEmpty: Boolean = false
)