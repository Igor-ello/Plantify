package com.example.myplants.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.db.AppDatabase
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.plants.PlantsViewModelFactory
import com.example.myplants.ui.navigation.NavigationDrawer
import com.example.myplants.ui.navigation.PlantsNavHost

@Composable
fun PlantsApp() {
    val viewModel: PlantsViewModel = viewModel(
        factory = PlantsViewModelFactory(
            AppDatabase.getInstance(LocalContext.current.applicationContext).plantDao
        )
    )
    val navController = rememberNavController()

    NavigationDrawer(navController = navController, viewModel = viewModel) { innerPadding ->
        PlantsNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}