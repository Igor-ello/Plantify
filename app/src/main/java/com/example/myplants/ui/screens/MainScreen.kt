package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplants.core.utils.Routes
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.viewmodels.MainViewModel
import com.example.myplants.ui.viewmodels.SearchSortViewModel
import com.example.myplants.ui.viewmodels.UiStateViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    searchSortViewModel: SearchSortViewModel,
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
            IconButton(onClick = onAddPlant) { Icon(Icons.Default.Add, contentDescription = "Add") }
        }
    }

    val plantsWithPhotos by viewModel.plants.observeAsState(emptyList())
    val genusMap by viewModel.genusMap.observeAsState(emptyMap())

    Column(modifier = modifier.fillMaxSize()) {
        SearchSortBar(searchSortViewModel)

        GroupedPlantList(
            plants = plantsWithPhotos,
            genusMap = genusMap,
            searchSortViewModel = searchSortViewModel,
            onPlantClick = onPlantClick,
            onToggleFavorite = { pw -> viewModel.toggleFavorite(pw.plant) },
            onToggleWishlist = { pw -> viewModel.toggleWishlist(pw.plant) },
            onNavigateToGenusDetail = { genusId ->
                navController.navigate(Routes.GenusDetail.createRoute(genusId))
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}