package com.example.myplants.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import com.example.myplants.ui.FakeAppContainer
import com.example.myplants.ui.componets.plant_card.GenusCard
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

    val groupedPlants = plantsWithPhotos.groupBy { it.plant.main.genus }
    LazyColumn(
        state = listState,
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        groupedPlants.forEach { (genus, plants) ->
            item(key = genus) {
                GenusCard(
                    genus = genus,
                    plants = plants,
                    onPlantClick = onPlantClick,
                    onToggleFavorite = { pw -> viewModel.toggleFavorite(pw.plant) },
                    onToggleWishlist = { pw -> viewModel.toggleWishlist(pw.plant) },
                    onEditGenus = { newGenus ->
                        plants.forEach { pw ->
                            viewModel.updatePlant(pw.plant.copy(main = pw.plant.main.copy(genus = newGenus)))
                        }
                    }
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PlantScreenPreview() {
    MyPlantsTheme {
        val fakePlants = listOf(
            PlantWithPhotos(
                plant = Plant(
                    id = 1,
                    main = MainInfo(
                        species = "Rose Red",
                        genus = "Rose",
                    ),
                    state = StateInfo(isFavorite = true)
                ),
                photos = emptyList()
            ),

            PlantWithPhotos(
                plant = Plant(
                    id = 2,
                    main = MainInfo(
                        species = "Tulip Yellow",
                        genus = "Tulipa"
                    ),
                    state = StateInfo(isWishlist = true)
                ),
                photos = emptyList()
            )
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
