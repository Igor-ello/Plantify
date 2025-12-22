package com.greencore.plantify.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateSingleTop(route: String, popUpToRoute: String) {
    navigate(route) {
        popUpTo(popUpToRoute) { inclusive = false }
        launchSingleTop = true
    }
}