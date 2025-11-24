package com.example.myplants.core.ui.system

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun ComponentActivity.configureSystemBars() {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(
            scrim = Color.White.toArgb()
        )
    )
}