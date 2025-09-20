package com.example.myplants.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.R
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.utils.Routes
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScaffold(
    viewModel: PlantsViewModel,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val plantId = navBackStackEntry?.arguments?.getString("plantId")?.toLongOrNull()
    val plantName: String? = if (plantId != null) {
        viewModel.getPlantById(plantId)?.name
    } else {
        null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when {
                        currentRoute?.startsWith(Routes.PLANT_DETAIL) == true ->
                            plantName ?: "Loading..."
                        currentRoute == Routes.ALL_PLANTS -> R.string.nav_drawer_all_plants
                        currentRoute == Routes.FAVORITES -> R.string.nav_drawer_favorites
                        currentRoute == Routes.SETTINGS -> R.string.nav_drawer_settings
                        currentRoute == Routes.HELP -> R.string.nav_drawer_help
                        else -> "My Plants"
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Navigation menu"
                        )
                    }
                },
                actions = {
                    if (currentRoute == Routes.ALL_PLANTS) {
                        IconButton(
                            onClick = {
                                navController.navigate(Routes.ADD_PLANT)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add plant"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Modifier.padding(innerPadding)
    }
}