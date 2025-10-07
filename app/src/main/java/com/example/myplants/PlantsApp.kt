package com.example.myplants


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.core.di.AppContainer
import com.example.myplants.ui.navigation.NavigationDrawer
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.viewmodels.UiStateViewModel

@Composable
fun PlantsApp(appContainer: AppContainer) {
    val navController = rememberNavController()

    val uiStateViewModel: UiStateViewModel = viewModel()

    NavigationDrawer(
        navController = navController,
        uiStateViewModel = uiStateViewModel
    ) { innerPadding ->
        PlantsNavHost(
            navController = navController,
            appContainer = appContainer,
            uiStateViewModel = uiStateViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
