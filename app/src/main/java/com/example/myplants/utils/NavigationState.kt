package com.example.myplants.utils

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavigationState(
    val navController: NavHostController,
    val drawerState: DrawerState,
    val scope: CoroutineScope
) {
    fun navigateTo(route: String) {
        scope.launch { drawerState.close() }
        navController.navigate(route) {
            // Очищаем back stack до корня
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Избегаем множественных копий того же destination
            launchSingleTop = true
            // Восстанавливаем состояние
            restoreState = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope()
): NavigationState {
    return remember(navController, drawerState, scope) {
        NavigationState(navController, drawerState, scope)
    }
}