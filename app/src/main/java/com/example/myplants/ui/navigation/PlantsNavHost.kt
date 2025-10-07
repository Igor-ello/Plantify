package com.example.myplants.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myplants.R
import com.example.myplants.core.di.AppContainerInterface
import com.example.myplants.ui.componets.SetupTopBar
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.AllPlantsScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.GenusDetailScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.PlantDetailScreen
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.screens.WishlistScreen
import com.example.myplants.core.utils.Routes
import com.example.myplants.ui.viewmodels.AddPlantViewModel
import com.example.myplants.ui.viewmodels.AddPlantViewModelFactory
import com.example.myplants.ui.viewmodels.GenusDetailViewModel
import com.example.myplants.ui.viewmodels.GenusDetailViewModelFactory
import com.example.myplants.ui.viewmodels.MainViewModel
import com.example.myplants.ui.viewmodels.MainViewModelFactory
import com.example.myplants.ui.viewmodels.PlantDetailViewModel
import com.example.myplants.ui.viewmodels.PlantDetailViewModelFactory
import com.example.myplants.ui.viewmodels.SettingsViewModel
import com.example.myplants.ui.viewmodels.SettingsViewModelFactory
import com.example.myplants.ui.viewmodels.UiStateViewModel


@Composable
fun PlantsNavHost(
    navController: NavHostController,
    appContainer: AppContainerInterface,
    uiStateViewModel: UiStateViewModel,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            appContainer.mainFacade
        )
    )
    NavHost(
        navController = navController,
        startDestination = Routes.AllPlants.route,
        modifier = modifier
    ) {
        composable(Routes.AllPlants.route) {
            AllPlantsScreen(
                viewModel = viewModel,
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

            val plantDetailViewModel: PlantDetailViewModel = viewModel(
                factory = PlantDetailViewModelFactory(plantId,
                    appContainer.plantWithPhotosRepository,
                    appContainer.plantRepository,
                    appContainer.photoRepository
                )
            )

            PlantDetailScreen(
                viewModel = plantDetailViewModel,
                navController = navController,
                uiStateViewModel = uiStateViewModel
            )
        }

        composable(Routes.AddPlant.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_add_plant, true)

            val addPlantsViewModel: AddPlantViewModel = viewModel(
                factory = AddPlantViewModelFactory(
                    appContainer.plantRepository,
                    appContainer.photoRepository
                )
            )

            AddPlantScreen(
                viewModel = addPlantsViewModel,
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

            val genusDetailViewModel: GenusDetailViewModel = viewModel(
                factory = GenusDetailViewModelFactory(genusId, appContainer.genusRepository)
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
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate(Routes.PlantDetail.createRoute(plant.id))
                }
            )
        }

        composable(Routes.Wishlist.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_wishlist)

            WishlistScreen(
                viewModel = viewModel,
                onPlantClick = { plant ->
                    navController.navigate(Routes.PlantDetail.createRoute(plant.id))
                }
            )
        }

        composable(Routes.Settings.route) {
            SetupTopBar(uiStateViewModel, R.string.screen_settings)

            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(appContainer.backupRepository)
            )

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