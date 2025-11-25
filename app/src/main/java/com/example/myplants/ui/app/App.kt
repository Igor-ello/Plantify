package com.example.myplants.ui.app


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myplants.ui.componets.scaffold.AppScaffold
import com.example.myplants.ui.navigation.AppNavHost
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel

@Composable
fun App(
    navController: NavHostController,
    topBarState: TopBarStateViewModel
) {
    AppScaffold(
        navController = navController,
        topBarState = topBarState
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            topBarState = topBarState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}