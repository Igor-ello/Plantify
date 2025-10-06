package com.example.myplants.ui.screens

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.models.Genus
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import com.example.myplants.ui.componets.cards.GenusCardMain
import com.example.myplants.ui.utils.Routes
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.viewmodels.UiStateViewModel
import java.util.Objects.toString

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
        groupedPlants.forEach { (genusName, plants) ->
            item(key = genusName) {
                var genus by remember { mutableStateOf<Genus?>(null) }
                LaunchedEffect(genusName) {
                    genus = viewModel.getOrCreateGenus(genusName)
                    Log.i("MyLog", genusName + toString(genus?.main?.genus))
                    Log.i("MyLog2", genusName + genus.toString())
                }

                if (genus != null) {
                    GenusCardMain(
                        genus = genus!!,
                        plants = plants,
                        onPlantClick = onPlantClick,
                        onToggleFavorite = { pw -> viewModel.toggleFavorite(pw.plant) },
                        onToggleWishlist = { pw -> viewModel.toggleWishlist(pw.plant) },
                        onNavigateToGenusDetail = { genusId ->
                            navController.navigate(Routes.GenusDetail.createRoute(genusId))
                        }
                    )
                }
            }
        }
    }
}