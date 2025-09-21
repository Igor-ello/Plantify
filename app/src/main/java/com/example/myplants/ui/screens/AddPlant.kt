package com.example.myplants.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.plant_card.PlantCardMax


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddPlant(
    viewModel: PlantsViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val newPlantWithPhotos by viewModel.newPlantWithPhotos.observeAsState(
        PlantWithPhotos(plant = Plant(name = "", species = ""), photos = emptyList())
    )

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlantCardMax(
                plantWithPhotos = newPlantWithPhotos,
                editable = true,
                onValueChange = { updatedPlant ->
                    viewModel.updateNewPlant(updatedPlant)
                }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        viewModel.clearNewPlant()
                        onCancel()
                    }
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        viewModel.saveNewPlant()
                        onSave()
                    },
                    enabled = newPlantWithPhotos.plant.name.isNotBlank() &&
                            newPlantWithPhotos.plant.species.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        }
    }
}
