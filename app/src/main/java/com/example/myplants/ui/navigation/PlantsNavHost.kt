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
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.AllPlantsScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.PlantDetailScreen
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.screens.WishlistScreen
import com.example.myplants.ui.utils.Routes


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    viewModel: PlantsViewModel,
    uiStateViewModel: UiStateViewModel,
    modifier: Modifier = Modifier
) {
    var title: String
    NavHost(
        navController = navController,
        startDestination = Routes.ALL_PLANTS,
        modifier = modifier
    ) {
        composable(Routes.ALL_PLANTS) {
            AllPlantsScreen(
                viewModel = viewModel,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate("${Routes.PLANT_DETAIL}/${plantWithPhotos.plant.id}")
                },
                onAddPlant = { navController.navigate(Routes.ADD_PLANT) },
                uiStateViewModel = uiStateViewModel,
                navController = navController
            )
        }

        composable("${Routes.PLANT_DETAIL}/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toLongOrNull()
            if (plantId == null) { LaunchedEffect(Unit) { navController.popBackStack() }; return@composable }

            PlantDetailScreen(
                plantId = plantId,
                viewModel = viewModel,
                navController = navController,
                uiStateViewModel = uiStateViewModel
            )
        }

        composable(Routes.ADD_PLANT) {
            title = stringResource(R.string.screen_add_plant)

            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(title)
                uiStateViewModel.setTopBarActions(null)
                uiStateViewModel.showBackButton(true)
            }

            AddPlantScreen(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(Routes.FAVORITES) {
            title = stringResource(R.string.screen_favorites)

            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(title)
                uiStateViewModel.setTopBarActions(null)
            }

            FavoritesScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate("${Routes.PLANT_DETAIL}/${plant.id}")
                }
            )
        }

        composable(Routes.WISHLIST) {
            title = stringResource(R.string.screen_wishlist)

            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(title)
                uiStateViewModel.setTopBarActions(null)
            }

            WishlistScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            title = stringResource(R.string.screen_settings)

            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(title)
                uiStateViewModel.setTopBarActions(null)
            }

            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HELP) {
            title = stringResource(R.string.screen_help_feedback)

            LaunchedEffect(Unit) {
                uiStateViewModel.setDrawerTitle(title)
                uiStateViewModel.setTopBarActions(null)
            }
            HelpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}