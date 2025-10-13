package com.example.myplants


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.ui.navigation.NavigationDrawer
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.viewmodels.UiStateViewModel

@Composable
fun PlantsApp() {
    val navController = rememberNavController()

    val uiStateViewModel: UiStateViewModel = hiltViewModel()

    NavigationDrawer(
        navController = navController,
        uiStateViewModel = uiStateViewModel
    ) { innerPadding ->
        PlantsNavHost(
            navController = navController,
            uiStateViewModel = uiStateViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
