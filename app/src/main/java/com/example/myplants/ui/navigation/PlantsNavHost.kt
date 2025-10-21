package com.example.myplants.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myplants.R
import com.example.myplants.ui.componets.SetupTopBar
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.GenusDetailScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.PlantDetailScreen
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.screens.WishlistScreen
import com.example.myplants.core.utils.Routes
import com.example.myplants.ui.screens.MainScreen
import com.example.myplants.ui.viewmodels.AddPlantViewModel
import com.example.myplants.ui.viewmodels.GenusDetailViewModel
import com.example.myplants.ui.viewmodels.MainViewModel
import com.example.myplants.ui.viewmodels.PlantDetailViewModel
import com.example.myplants.ui.viewmodels.SettingsViewModel
import com.example.myplants.ui.viewmodels.UiStateViewModel


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    uiStateViewModel: UiStateViewModel,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.AllPlants.route,
        modifier = modifier
    ) {
        composable(Routes.AllPlants.route) {
            MainScreen(
                viewModel = mainViewModel,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(Routes.PlantDetail.createRoute(plantWithPhotos.plant.id))
                },
                onAddPlant = { navController.navigate(Routes.AddPlant.route) },
                navController = navController,
                uiStateViewModel = uiStateViewModel
            )
        }

        composable(
            Routes.PlantDetail.route,
            arguments = listOf(navArgument("plantId") { type = NavType.LongType })
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getLong("plantId")
            if (plantId == null) {
                LaunchedEffect(Unit) { navController.popBackStack() }
                return@composable
            }

            val plantDetailViewModel: PlantDetailViewModel = hiltViewModel()

            PlantDetailScreen(
                viewModel = plantDetailViewModel,
                navController = navController,
                uiStateViewModel = uiStateViewModel
            )
        }

        composable(Routes.AddPlant.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_add_plant, true)
            val addPlantViewModel: AddPlantViewModel = hiltViewModel()

            AddPlantScreen(
                viewModel = addPlantViewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            Routes.GenusDetail.route,
            arguments = listOf(navArgument("genusId") { type = NavType.LongType })
        ) { backStackEntry ->
            val genusId = backStackEntry.arguments?.getLong("genusId")
            if (genusId == null) {
                LaunchedEffect(Unit) { navController.popBackStack() }
                return@composable
            }

            val genusDetailViewModel: GenusDetailViewModel = hiltViewModel(
//                key = "GenusDetail_$genusId"
            )

            GenusDetailScreen(
                viewModel = genusDetailViewModel,
                navController = navController,
                uiStateViewModel = uiStateViewModel
            )
        }

        composable(Routes.Favorites.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_favorites)
            FavoritesScreen(
                viewModel = mainViewModel,
                onPlantClick = { plant ->
                    navController.navigate(Routes.PlantDetail.createRoute(plant.id))
                }
            )
        }

        composable(Routes.Wishlist.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_wishlist)
            WishlistScreen(
                viewModel = mainViewModel,
                onPlantClick = { plant ->
                    navController.navigate(Routes.PlantDetail.createRoute(plant.id))
                }
            )
        }

        composable(Routes.Settings.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_settings)

            val settingsViewModel: SettingsViewModel = hiltViewModel()

            SettingsScreen(
                viewModel = settingsViewModel
            )
        }

        composable(Routes.Help.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_help_feedback)
            HelpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}