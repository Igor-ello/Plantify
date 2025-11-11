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
import com.example.myplants.ui.componets.common.cards.PlantCardMain
import com.example.myplants.ui.viewmodels.MainViewModel

@Composable
fun WishlistScreen (
    viewModel: MainViewModel,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    val wishlist by viewModel.wishlist.observeAsState(emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(wishlist) { plantWithPhotos ->
            PlantCardMain(
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