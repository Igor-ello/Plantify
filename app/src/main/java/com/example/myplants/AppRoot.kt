@file:JvmName("MainAppKt")

package com.example.myplants

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.ui.app.App
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel

@Composable
fun AppRoot() {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val navController = rememberNavController()

    App(
        navController = navController,
        topBarState = topBarState
    )
}
