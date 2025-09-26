package com.example.myplants


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.ui.AppContainer
import com.example.myplants.ui.navigation.NavigationDrawer
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.viewmodels.PlantsViewModelFactory
import com.example.myplants.ui.viewmodels.UiStateViewModel

@Composable
fun PlantsApp(appContainer: AppContainer) {
    val navController = rememberNavController()

    val plantsViewModel: PlantsViewModel = viewModel(
        factory = PlantsViewModelFactory(
            appContainer.plantRepository,
            appContainer.backupRepository
        )
    )
    val uiStateViewModel: UiStateViewModel = viewModel()

    NavigationDrawer(
        navController = navController,
        uiStateViewModel = uiStateViewModel
    ) { innerPadding ->
        PlantsNavHost(
            navController = navController,
            viewModel = plantsViewModel,
            uiStateViewModel = uiStateViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
