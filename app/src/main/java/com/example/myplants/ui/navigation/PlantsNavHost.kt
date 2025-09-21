package com.example.myplants.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myplants.R
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.screens.AddPlant
import com.example.myplants.ui.screens.AllPlants
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.PlantDetail
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.utils.Routes


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    viewModel: PlantsViewModel,
    uiStateViewModel: UiStateViewModel,
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
                onPlantClick = { plant -> navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}") },
                onAddPlant = { navController.navigate(Routes.ADD_PLANT) },
                uiStateViewModel = uiStateViewModel,
                navController = navController
            )
        }

        composable("${Routes.PLANT_DETAIL}/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toLongOrNull()
            if (plantId == null) { LaunchedEffect(Unit) { navController.popBackStack() }; return@composable }

            PlantDetail(
                plantId = plantId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                uiStateViewModel = uiStateViewModel // обязательно передаём
            )
        }

        composable(Routes.ADD_PLANT) {
            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle("AddPlant")
                uiStateViewModel.showBackButton(true)
                uiStateViewModel.setTopBarActions(null)
            }

            AddPlant(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FAVORITES) {
            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle("Favourites")
                uiStateViewModel.setTopBarActions(null)
            }

            FavoritesScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}")
                }
            )
        }

        composable(Routes.SETTINGS) {
            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle("Settings")
                uiStateViewModel.setTopBarActions(null)
            }

            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HELP) {
            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle("Help & Feedback")
                uiStateViewModel.setTopBarActions(null)
            }
            HelpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}