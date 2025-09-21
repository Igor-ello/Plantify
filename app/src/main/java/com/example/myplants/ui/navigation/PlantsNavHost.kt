package com.example.myplants.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    val all_plants = stringResource(R.string.nav_drawer_all_plants)
    NavHost(
        navController = navController,
        startDestination = Routes.ALL_PLANTS,
        modifier = modifier
    ) {
        composable(Routes.ALL_PLANTS) {
            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(all_plants)
                uiStateViewModel.showBackButton(false)
                uiStateViewModel.setTopBarActions {
                    IconButton(onClick = { navController.navigate(Routes.ADD_PLANT) }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }

            AllPlants(
                viewModel = viewModel,
                onPlantClick = { plant -> navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}") },
                onAddPlant = { navController.navigate(Routes.ADD_PLANT) }
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

        composable(Routes.FAVORITES) {
            FavoritesScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}")
                }
            )
        }

        composable(Routes.ADD_PLANT) {
            AddPlant(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
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