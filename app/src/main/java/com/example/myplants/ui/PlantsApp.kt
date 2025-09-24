package com.example.myplants.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myplants.backup.BackupRepository
import com.example.myplants.db.AppDatabase
import com.example.myplants.plants.PlantRepository
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.plants.PlantsViewModelFactory
import com.example.myplants.ui.navigation.NavigationDrawer
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.navigation.UiStateViewModel

@Composable
fun PlantsApp() {
    val context = LocalContext.current.applicationContext
    val database = AppDatabase.getInstance(context)
    val repository = PlantRepository(
        database.plantDao, database.plantPhotoDao
    )
    val backupRepository = BackupRepository(
        database.plantDao, database.plantPhotoDao, context
    )

    val plantsViewModel: PlantsViewModel = viewModel(
        factory = PlantsViewModelFactory(repository, backupRepository)
    )
    val uiStateViewModel: UiStateViewModel = viewModel()
    val navController = rememberNavController()

    NavigationDrawer(
        navController = navController,
        uiStateViewModel = uiStateViewModel
    ) { innerPadding ->
        PlantsNavHost(
            navController = navController,
            viewModel = plantsViewModel,
            uiStateViewModel = uiStateViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
