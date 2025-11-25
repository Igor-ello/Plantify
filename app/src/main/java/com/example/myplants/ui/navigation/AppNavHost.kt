package com.example.myplants.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myplants.core.domain.common.Routes
import com.example.myplants.ui.screens.genus.GenusDetailScreen
import com.example.myplants.ui.screens.help.HelpScreen
import com.example.myplants.ui.screens.main.MainScreen
import com.example.myplants.ui.screens.plant.add.AddPlantScreen
import com.example.myplants.ui.screens.plant.detail.PlantDetailScreen
import com.example.myplants.ui.screens.plant.state.PlantStateScreen
import com.example.myplants.ui.screens.plant.state.PlantStateType
import com.example.myplants.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AllPlants.route,
        modifier = modifier
    ) {
        composable(Routes.AllPlants.route) {
            MainScreen(
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(Routes.PlantDetail.createRoute(plantWithPhotos.plant.id))
                },
                onAddPlant = { navController.navigate(Routes.AddPlant.route) },
                onNavigateToGenusDetail = { genusId ->
                    navController.navigate(Routes.GenusDetail.createRoute(genusId))
                }
            )
        }

        composable(
            Routes.PlantDetail.route,
            arguments = listOf(navArgument("plantId") { type = NavType.LongType })
        ) { backStackEntry ->
            PlantDetailScreen(
                navController = navController
            )
        }

        composable(Routes.AddPlant.route) {
            AddPlantScreen(
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            Routes.GenusDetail.route,
            arguments = listOf(navArgument("genusId") { type = NavType.LongType })
        ) {
            GenusDetailScreen(
                navController = navController
            )
        }

        composable(Routes.Favorites.route) {
            PlantStateScreen(
                listType = PlantStateType.FAVORITES,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(
                        Routes.PlantDetail.createRoute(plantWithPhotos.plant.id)
                    )
                }
            )
        }

        composable(Routes.Wishlist.route) {
            PlantStateScreen(
                listType = PlantStateType.WISHLIST,
                onPlantClick = { plantWithPhotos ->
                    navController.navigate(
                        Routes.PlantDetail.createRoute(plantWithPhotos.plant.id)
                    )
                }
            )
        }

        composable(Routes.Settings.route) {
            SettingsScreen()
        }

        composable(Routes.Help.route) {
            HelpScreen()
        }
    }
}