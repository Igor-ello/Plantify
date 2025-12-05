package com.example.myplants.ui.componets.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

sealed class AppIconType {
    data class MaterialIcon(
        val imageVector: ImageVector,
        val contentDescription: String,
        val size: Dp,
        val tintColor: Color? = null
    ) : AppIconType()

    data class TintedIcon(
        val baseIcon: MaterialIcon,
        val tintColor: Color
    ) : AppIconType()

    data class AnimatedIcon(
        val startIcon: TintedIcon,
        val endIcon: TintedIcon,
        val isAnimated: Boolean = false
    ) : AppIconType()
}