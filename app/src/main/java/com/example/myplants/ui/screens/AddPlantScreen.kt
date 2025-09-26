package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.myplants.ui.viewmodels.PlantsViewModel
import com.example.myplants.ui.componets.plant_card.PlantCardMax


@Composable
fun AddPlantScreen(
    viewModel: PlantsViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val newPlant by viewModel.newPlant.observeAsState(Plant(name = "", species = ""))
    val newPhotos by viewModel.newPlantPhotos.observeAsState(emptyList())
    val newPlantWithPhotos = PlantWithPhotos(plant = newPlant, photos = newPhotos)

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
                },
                onPhotosChanged = { updatedPhotos ->
                    viewModel.updateNewPlantPhotos(updatedPhotos)
                }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        viewModel.clearNewPlant()
                        viewModel.updateNewPlantPhotos(emptyList())
                        onCancel()
                    }
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        viewModel.saveNewPlant(newPhotos)
                        onSave()
                    },
                    enabled = newPlant.name.isNotBlank() && newPlant.species.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        }
    }
}
