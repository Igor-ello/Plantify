package com.example.myplants.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.R
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.utils.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavController,
    viewModel: PlantsViewModel,
    content: @Composable (PaddingValues) -> Unit
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "My Plants",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()

                    // Plants Section
                    Text(
                        text = "Your section",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.nav_drawer_all_plants)) },
                        selected = currentRoute == Routes.ALL_PLANTS,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.ALL_PLANTS) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.nav_drawer_favorites)) },
                        selected = currentRoute == Routes.FAVORITES,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.FAVORITES) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // App Section
                    Text(
                        text = "App section",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = currentRoute == Routes.SETTINGS,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = stringResource(R.string.nav_drawer_settings)) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.SETTINGS)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help & Feedback") },
                        selected = currentRoute == Routes.HELP,
                        icon = { Icon(Icons.Outlined.Info, contentDescription = stringResource(R.string.nav_drawer_help)) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.HELP)
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        when {
                            currentRoute?.startsWith(Routes.PLANT_DETAIL) == true ->
                                plantName ?: "Loading..."
                            currentRoute == Routes.ALL_PLANTS ->  stringResource(R.string.nav_drawer_all_plants)
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
            content(innerPadding)
        }
    }
}