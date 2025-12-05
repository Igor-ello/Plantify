package com.example.myplants.ui.componets.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppIcon(
    icon: AppIconType,
    modifier: Modifier = Modifier,
) {
    when (icon) {
        is AppIconType.MaterialIcon -> {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = icon.contentDescription,
                modifier = modifier.size(icon.size),
            )
        }
        is AppIconType.TintedIcon -> {
            Icon(
                imageVector = icon.baseIcon.imageVector,
                contentDescription = icon.baseIcon.contentDescription,
                modifier = modifier.size(icon.baseIcon.size),
                tint = icon.tintColor
            )
        }
        is AppIconType.AnimatedIcon -> {
            AppIcon(
                icon = icon.startIcon,
                modifier = modifier
            )
        }
    }
}