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
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.plant_card.PlantCardMin

@Composable
fun AllPlants(
    viewModel: PlantsViewModel,
    onPlantClick: (Plant) -> Unit,
    onAddPlant: () -> Unit,
    modifier: Modifier = Modifier
) {
    val plants by viewModel.plants.observeAsState(emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(plants) { plant ->
            PlantCardMin(
                plant = plant,
                editable = false,
                onValueChange = {},
                onClick = { onPlantClick(plant) }
            )
        }
    }
}