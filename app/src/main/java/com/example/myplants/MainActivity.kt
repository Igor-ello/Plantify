package com.example.myplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myplants.db.AppDatabase
import com.example.myplants.models.Plant
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.plants.PlantsViewModelFactory
import com.example.myplants.ui.AllPlants
import com.example.myplants.ui.plant_card.PlantCard
import com.example.myplants.ui.screens.AddPlantScreen
import com.example.myplants.ui.screens.FavoritesScreen
import com.example.myplants.ui.screens.HelpScreen
import com.example.myplants.ui.screens.SelectedPlantScreen
import com.example.myplants.ui.screens.SettingsScreen
import com.example.myplants.ui.theme.MyPlantsTheme
import com.example.myplants.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getInstance(this.application).plantDao
        val viewModelFactory = PlantsViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[PlantsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            MyPlantsTheme {
                val navController = rememberNavController()

                NavigationDrawer(navController = navController) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.ALL_PLANTS,
                        modifier = Modifier.padding(innerPadding)
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantScreenPreview() {
    MyPlantsTheme {
        // Предварительный просмотр без ViewModel
        //PlantScreen(viewModel = PlantsViewModel(dao = /* передайте здесь dao для превью */))
    }
}
