package com.example.myplants.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.screens.AllPlants
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.SelectedPlantScreen
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.utils.Routes


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    viewModel: PlantsViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ALL_PLANTS,
        modifier = modifier
    ) {
        composable(Routes.ALL_PLANTS) {
            AllPlants(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.SELECTED_PLANT}/${plant.id}")
                },
                onAddPlant = {
                    navController.navigate(Routes.ADD_PLANT)
                }
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.SELECTED_PLANT}/${plant.id}")
                }
            )
        }
        composable("${Routes.SELECTED_PLANT}/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toLongOrNull()
            SelectedPlantScreen(
                viewModel = viewModel,
                plantId = plantId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ADD_PLANT) {
            AddPlantScreen(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HELP) {
            HelpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}