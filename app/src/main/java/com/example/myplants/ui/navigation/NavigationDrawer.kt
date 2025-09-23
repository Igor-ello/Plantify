package com.example.myplants.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
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
import com.example.myplants.ui.utils.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavController,
    uiStateViewModel: UiStateViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""

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
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()

                    // Plants Section
                    Text(
                        text = stringResource(R.string.plants_section),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.screen_all_plants)) },
                        selected = currentRoute == Routes.ALL_PLANTS
                                || currentRoute.startsWith(Routes.PLANT_DETAIL)
                                || currentRoute.startsWith(Routes.ADD_PLANT),
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.ALL_PLANTS) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.screen_favorites)) },
                        selected = (currentRoute == Routes.FAVORITES),
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.FAVORITES) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.screen_wishlist)) },
                        selected = (currentRoute == Routes.WISHLIST),
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.WISHLIST) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // App Section
                    Text(
                        text = stringResource(R.string.app_section),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.screen_settings)) },
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        selected = (currentRoute == Routes.SETTINGS),
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.SETTINGS) {
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.screen_help_feedback)) },
                        icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                        selected = (currentRoute == Routes.HELP),
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.HELP){
                                popUpTo(Routes.ALL_PLANTS) { inclusive = false }
                            }
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
                    title = { Text(uiStateViewModel.drawerTitle) },
                    navigationIcon = {
                        if (uiStateViewModel.showBackButton) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.Close, contentDescription = "Back")
                            }
                        } else {
                            IconButton(onClick = {
                                scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    },
                    actions = {
                        uiStateViewModel.topBarActions?.invoke(this)
                    }
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}
