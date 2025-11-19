package com.example.myplants.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.core.utils.Routes
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.common.cards.GenusCardMain
import com.example.myplants.ui.viewmodels.MainViewModel
import com.example.myplants.ui.viewmodels.UiStateViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onAddPlant: () -> Unit,
    uiStateViewModel: UiStateViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Состояние TopBar
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

    // Отображение групп растений
    val plantsWithPhotos by viewModel.plants.observeAsState(emptyList())
    val genusMap by viewModel.genusMap.observeAsState(emptyMap())
    val listState = rememberLazyListState()

    val groupedPlants = plantsWithPhotos.groupBy { it.plant.main.genus }
    LazyColumn(
        state = listState,
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        groupedPlants.entries.forEachIndexed {index, (genusName, plants) ->
            item(key = genusName) {
                val genus = genusMap[genusName]
                if (genus != null) {
                    GenusCardMain(
                        genus = genus,
                        plants = plants,
                        onPlantClick = onPlantClick,
                        onToggleFavorite = { pw -> viewModel.toggleFavorite(pw.plant) },
                        onToggleWishlist = { pw -> viewModel.toggleWishlist(pw.plant) },
                        onNavigateToGenusDetail = { genusId ->
                            navController.navigate(Routes.GenusDetail.createRoute(genusId))
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}