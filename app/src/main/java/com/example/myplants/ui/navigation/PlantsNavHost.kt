package com.example.myplants.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.screens.AllPlants
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.PlantDetail
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
                    navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}")
                },
                onAddPlant = {
                    navController.navigate(Routes.ADD_PLANT)
                }
            )
        }
        composable("${Routes.PLANT_DETAIL}/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toLongOrNull()

            if (plantId == null) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
                return@composable
            }

            PlantDetail(
                plantId = plantId ?: -1L,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}")
                }
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