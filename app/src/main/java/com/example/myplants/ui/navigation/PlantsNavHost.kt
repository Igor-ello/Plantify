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
import com.example.myplants.core.domain.common.Routes
import com.example.myplants.ui.screens.genus.GenusDetailScreen
import com.example.myplants.ui.screens.genus.GenusDetailViewModel
import com.example.myplants.ui.screens.help.HelpScreen
import com.example.myplants.ui.screens.main.MainScreen
import com.example.myplants.ui.screens.main.MainScreenStateHolder
import com.example.myplants.ui.screens.plant.add.AddPlantScreen
import com.example.myplants.ui.screens.plant.add.AddPlantViewModel
import com.example.myplants.ui.screens.plant.detail.PlantDetailScreen
import com.example.myplants.ui.screens.plant.detail.PlantDetailViewModel
import com.example.myplants.ui.screens.plant.state.PlantStateScreen
import com.example.myplants.ui.screens.plant.state.PlantStateType
import com.example.myplants.ui.screens.plant.state.PlantStateViewModel
import com.example.myplants.ui.screens.settings.SettingsScreen
import com.example.myplants.ui.screens.settings.SettingsViewModel
import com.example.myplants.ui.screens.topbar.SetupTopBar
import com.example.myplants.ui.screens.topbar.UiStateViewModel


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    uiStateViewModel: UiStateViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AllPlants.route,
        modifier = modifier
    ) {
        composable(Routes.AllPlants.route) {
            val stateHolder: MainScreenStateHolder = hiltViewModel()
            MainScreen(
                stateHolder = stateHolder,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(Routes.PlantDetail.createRoute(plantWithPhotos.plant.id))
                },
                onAddPlant = { navController.navigate(Routes.AddPlant.route) },
                onNavigateToGenusDetail = { genusId ->
                    navController.navigate(Routes.GenusDetail.createRoute(genusId))
                },
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
            val plantListViewModel: PlantStateViewModel = hiltViewModel()
            PlantStateScreen(
                viewModel = plantListViewModel,
                listType = PlantStateType.FAVORITES,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(
                        Routes.PlantDetail.createRoute(plantWithPhotos.plant.id)
                    )
                }
            )
        }

        composable(Routes.Wishlist.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_wishlist)
            val plantListViewModel: PlantStateViewModel = hiltViewModel()
            PlantStateScreen(
                viewModel = plantListViewModel,
                listType = PlantStateType.WISHLIST,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(
                        Routes.PlantDetail.createRoute(plantWithPhotos.plant.id)
                    )
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