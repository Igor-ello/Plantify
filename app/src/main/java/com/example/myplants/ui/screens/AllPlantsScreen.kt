package com.example.myplants.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.myplants.data.plant.FakePlantRepository
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.FakeAppContainer
import com.example.myplants.ui.componets.plant_card.PlantCardMin
import com.example.myplants.ui.navigation.PlantsNavHost
import com.example.myplants.ui.theme.MyPlantsTheme
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.viewmodels.UiStateViewModel

@OptIn(ExperimentalFoundationApi::class)
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
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        items(
            items = plantsWithPhotos,
            key = { it.plant.id },
            contentType = { "plant" }
        ) { plantWithPhotos ->
            val plantId = plantWithPhotos.plant.id

            // safer: remember the whole item so lambda captures the correct instance
//            val onClick = remember(plantId) { { onPlantClick(plantWithPhotos) } }
//            val onToggleFavorite = remember(plantId) { { viewModel.toggleFavorite(plantWithPhotos.plant) } }
//            val onToggleWishlist = remember(plantId) { { viewModel.toggleWishlist(plantWithPhotos.plant) } }

            val onClick: (Plant) -> Unit = { onPlantClick(plantWithPhotos) }
            val onToggleFavorite: (PlantWithPhotos) -> Unit = { viewModel.toggleFavorite(it.plant) }
            val onToggleWishlist: (PlantWithPhotos) -> Unit = { viewModel.toggleWishlist(it.plant) }

            PlantCardMin(
                plantWithPhotos = plantWithPhotos,
                editable = false,
                onValueChange = {},
                onClick = onClick,
                onToggleFavorite = onToggleFavorite,
                onToggleWishlist = onToggleWishlist,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
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
