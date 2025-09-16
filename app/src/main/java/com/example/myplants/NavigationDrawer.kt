package com.example.myplants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.utils.NavigationState
import com.example.myplants.utils.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navigationState: NavigationState,
    currentRoute: String?,
    content: @Composable (PaddingValues) -> Unit
) {
    ModalNavigationDrawer(
        drawerState = navigationState.drawerState,
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
                        label = { Text("All Plants") },
                        selected = currentRoute == Routes.ALL_PLANTS,
                        onClick = { navigationState.navigateTo(Routes.ALL_PLANTS) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Favorites") },
                        selected = currentRoute == Routes.FAVORITES,
                        onClick = { navigationState.navigateTo(Routes.FAVORITES) }
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
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                        onClick = { navigationState.navigateTo(Routes.SETTINGS) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help & Feedback") },
                        selected = currentRoute == Routes.HELP,
                        icon = { Icon(Icons.Outlined.Info, contentDescription = "Help") },
                        onClick = { navigationState.navigateTo(Routes.HELP) }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(getScreenTitle(currentRoute)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigationState.scope.launch {
                                    navigationState.drawerState.apply {
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
                    }
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

private fun getScreenTitle(route: String?): String {
    return when (route) {
        Routes.ALL_PLANTS -> "All Plants"
        Routes.FAVORITES -> "Favorites"
        Routes.SELECTED_PLANT -> "Plant Details"
        Routes.ADD_PLANT -> "Add Plant"
        Routes.SETTINGS -> "Settings"
        Routes.HELP -> "Help & Feedback"
        else -> "My Plants"
    }
}