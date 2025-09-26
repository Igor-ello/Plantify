package com.example.myplants.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myplants.data.backup.FakeBackupRepository
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.data.plant.FakePlantRepository
import com.example.myplants.ui.AppContainer
import com.example.myplants.ui.FakeAppContainer
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.viewmodels.UiStateViewModel
import com.example.myplants.ui.componets.plant_card.PlantCardMin
import com.example.myplants.ui.theme.MyPlantsTheme

@Composable
fun AllPlantsScreen(
    viewModel: PlantsViewModel,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onAddPlant: () -> Unit,
    uiStateViewModel: UiStateViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry?.destination?.route) {
        uiStateViewModel.setDrawerTitle("All Plants")
        uiStateViewModel.showBackButton(false)
        uiStateViewModel.setTopBarActions {
            IconButton(onClick = onAddPlant) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }

    val plantsWithPhotos by viewModel.plants.observeAsState(emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(plantsWithPhotos) { plantWithPhotos ->
            PlantCardMin(
                plantWithPhotos = plantWithPhotos,
                editable = false,
                onValueChange = {},
                onClick = { onPlantClick(plantWithPhotos) },
                onToggleFavorite = { viewModel.toggleFavorite(plantWithPhotos.plant) },
                onToggleWishlist = { viewModel.toggleWishlist(plantWithPhotos.plant) }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PlantScreenPreview() {
    MyPlantsTheme {
        val fakePlants = listOf(
            PlantWithPhotos(Plant(1, "Rose", "Red", isFavorite = true), emptyList()),
            PlantWithPhotos(Plant(2, "Tulip", "Yellow", isWishlist = true), emptyList())
        )

        val fakeRepo = FakePlantRepository(fakePlants)
        val fakeBackupRepo = FakeBackupRepository()
        val fakeContainer = FakeAppContainer(fakeRepo, fakeBackupRepo)
        val uiStateVm = UiStateViewModel()

        PlantsNavHost(
            navController = rememberNavController(),
            uiStateViewModel = uiStateVm,
            appContainer = fakeContainer
        )
    }
}
