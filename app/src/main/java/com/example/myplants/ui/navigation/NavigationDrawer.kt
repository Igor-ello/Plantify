package com.example.myplants.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
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
import com.example.myplants.core.utils.Routes
import com.example.myplants.ui.viewmodels.UiStateViewModel
import kotlinx.coroutines.CoroutineScope
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

    // Список элементов навигации
    val drawerItems = listOf(
        DrawerItemData(
            labelRes = R.string.screen_all_plants,
            route = Routes.AllPlants.route
        ),
        DrawerItemData(
            labelRes = R.string.screen_favorites,
            route = Routes.Favorites.route
        ),
        DrawerItemData(
            labelRes = R.string.screen_wishlist,
            route = Routes.Wishlist.route
        ),
        DrawerItemData(
            labelRes = R.string.screen_settings,
            route = Routes.Settings.route,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
        ),
        DrawerItemData(
            labelRes = R.string.screen_help_feedback,
            route = Routes.Help.route,
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) }
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        // Plants Section
                        item {
                            Text(
                                text = stringResource(R.string.plants_section),
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(drawerItems.filter { it.route in listOf(
                            Routes.AllPlants.route,
                            Routes.Favorites.route,
                            Routes.Wishlist.route
                        ) }) { item ->
                            DrawerItem(item, currentRoute, navController, drawerState, scope)
                        }

                        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

                        // App Section
                        item {
                            Text(
                                text = stringResource(R.string.app_section),
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(drawerItems.filter { it.route in listOf(
                            Routes.Settings.route,
                            Routes.Help.route
                        ) }) { item ->
                            DrawerItem(item, currentRoute, navController, drawerState, scope)
                        }
                    }

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
                                // TODO
                                Icon(Icons.Default.Close, contentDescription = "Back")
                            }
                        } else {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    },
                    actions = uiStateViewModel.topBarActions ?: {}
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

// -------------------- HELPER --------------------

data class DrawerItemData(
    @StringRes val labelRes: Int,
    val route: String,
    val icon: (@Composable (() -> Unit))? = null
)

@Composable
fun DrawerItem(
    item: DrawerItemData,
    currentRoute: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val selected = currentRoute.startsWith(item.route)
    NavigationDrawerItem(
        label = { Text(stringResource(item.labelRes)) },
        icon = item.icon,
        selected = selected,
        onClick = {
            scope.launch { drawerState.close() }
            navController.navigateSingleTop(item.route, Routes.AllPlants.route)
        }
    )
}

fun NavController.navigateSingleTop(route: String, popUpToRoute: String) {
    this.navigate(route) {
        popUpTo(popUpToRoute) { inclusive = false }
        launchSingleTop = true
    }
}

