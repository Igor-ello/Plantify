package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.componets.plant_card.PlantCardMin

@Composable
fun WishlistScreen (
    viewModel: PlantsViewModel,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    val wishlist by viewModel.wishlist.observeAsState(emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(wishlist) { plantWithPhotos ->
            PlantCardMin(
                plantWithPhotos = plantWithPhotos,
                editable = false,
                onValueChange = {},
                onClick = { onPlantClick(plantWithPhotos.plant) },
                onToggleFavorite = {},
                onToggleWishlist = { viewModel.toggleWishlist(plantWithPhotos.plant) }
            )
        }
    }
}