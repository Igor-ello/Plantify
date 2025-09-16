package com.example.myplants

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.utils.Routes
import com.example.myplants.utils.rememberNavigationState

@Composable
fun AppNavigation(context: Context, viewModel: PlantsViewModel) {
    val navigationState = rememberNavigationState()
    val navController = navigationState.navController

    // Получаем текущий маршрут
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationDrawer(
        navigationState = navigationState,
        currentRoute = currentRoute,
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.ALL_PLANTS,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.ALL_PLANTS) {
                    MainScreen(
                        context = context,
                        viewModel = viewModel,
                        onPlantClick = { plant ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "selectedPlant", plant
                            )
                            navController.navigate(Routes.SELECTED_PLANT)
                        },
                        onAddPlantClick = {
                            navController.navigate(Routes.ADD_PLANT)
                        }
                    )
                }

                composable(Routes.FAVORITES) {
                    FavoritesScreen(
                        viewModel = viewModel,
                        onPlantClick = { plant ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "selectedPlant", plant
                            )
                            navController.navigate(Routes.SELECTED_PLANT)
                        }
                    )
                }

                composable(Routes.SELECTED_PLANT) {
                    val plant = it.savedStateHandle.get<Plant>("selectedPlant")
                    if (plant != null) {
                        SelectedPlantScreen(
                            plant = plant,
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    } else {
                        // Обработка случая, когда plant == null
                        Text("Plant not found")
                    }
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
    )
}