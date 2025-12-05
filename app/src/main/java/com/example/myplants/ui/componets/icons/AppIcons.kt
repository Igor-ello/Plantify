package com.example.myplants.ui.componets.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.myplants.core.ui.theme.GreenPrimary
import com.example.myplants.core.ui.theme.RedPrimary

object AppIcons {
    // MaterialIcon (без перекраски)
    val AllPlantsNav = AppIconType.MaterialIcon(
        Icons.Default.Home, "All plants screen", IconSize.Navigation
    )
    val FavouriteNav = AppIconType.MaterialIcon(
        Icons.Default.Favorite, "Favourite screen", IconSize.Navigation
    )
    val WishlistNav = AppIconType.MaterialIcon(
        Icons.Default.ShoppingCart, "Wishlist screen", IconSize.Navigation
    )
    val SettingsNav = AppIconType.MaterialIcon(
        Icons.Default.Settings, "Settings screen", IconSize.Navigation
    )
    val InfoNav = AppIconType.MaterialIcon(
        Icons.Default.QuestionAnswer, "Help & Feedback screen", IconSize.Navigation
    )

    // TintedIcon
    val Favorite = AppIconType.TintedIcon(
        baseIcon = AppIconType.MaterialIcon(
            Icons.Default.Favorite, "Remove from favourite button", IconSize.Card
        ),
        tintColor = RedPrimary
    )

    val AddFavorite = AppIconType.TintedIcon(
        baseIcon = AppIconType.MaterialIcon(
            Icons.Default.FavoriteBorder, "Add to favourite button", IconSize.Card
        ),
        tintColor = RedPrimary
    )

    val Wishlist = AppIconType.TintedIcon(
        baseIcon = AppIconType.MaterialIcon(
            Icons.Default.ShoppingCart, "Remove from wishlist button", IconSize.Card
        ),
        tintColor = GreenPrimary
    )

    val AddWishlist = AppIconType.TintedIcon(
        baseIcon = AppIconType.MaterialIcon(
            Icons.Default.AddShoppingCart, "Add to wishlist button", IconSize.Card
        ),
        tintColor = GreenPrimary
    )

    // AnimatedIcon (меняются при нажатии)
    fun favoriteAnimated(isFavorite: Boolean): AppIconType {
        return AppIconType.AnimatedIcon(
            startIcon = if (isFavorite) Favorite else AddFavorite,
            endIcon = if (isFavorite) AddFavorite else Favorite,
            isAnimated = true
        )
    }

    fun wishlistAnimated(isWishlist: Boolean): AppIconType {
        return AppIconType.AnimatedIcon(
            startIcon = if (isWishlist) Wishlist else AddWishlist,
            endIcon = if (isWishlist) AddWishlist else Wishlist,
            isAnimated = true
        )
    }
}