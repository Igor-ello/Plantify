package com.example.myplants.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.navigation.UiStateViewModel
import com.example.myplants.ui.plant_card.PlantCardMax


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantDetail(
    plantId: Long,
    viewModel: PlantsViewModel,
    modifier: Modifier = Modifier,
    uiStateViewModel: UiStateViewModel? = null
) {
    val uiStateViewModel: UiStateViewModel = uiStateViewModel ?: viewModel<UiStateViewModel>()

    val allPlants by viewModel.plants.observeAsState(emptyList())
    val selectedPlantWithPhotos = allPlants.find { it.plant.id == plantId }

    if (selectedPlantWithPhotos == null) {
        Text("Plant not found", modifier = modifier.padding(16.dp))
        return
    }

    var isEditing by remember { mutableStateOf(false) }
    var editedPlant by remember { mutableStateOf(selectedPlantWithPhotos.plant) }
    var editedPhotos by remember { mutableStateOf(selectedPlantWithPhotos.photos) } // 👈 Локально храним фото

    // Обновляем TopBar в зависимости от режима
    LaunchedEffect(isEditing, editedPlant) {
        val title = if (isEditing) "Edit: ${editedPlant.name}" else editedPlant.name.ifBlank { "Plant" }
        uiStateViewModel.setDrawerTitle(title)
        uiStateViewModel.showBackButton(true)

        uiStateViewModel.setTopBarActions {
            if (isEditing) {
                IconButton(onClick = {
                    // Отменяем изменения
                    editedPlant = selectedPlantWithPhotos.plant
                    editedPhotos = selectedPlantWithPhotos.photos
                    isEditing = false
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                IconButton(
                    onClick = {
                        // Сохраняем изменения
                        viewModel.updatePlant(editedPlant, editedPhotos)
                        isEditing = false
                        uiStateViewModel.setDrawerTitle(editedPlant.name.ifBlank { "Plant" })
                    },
                    enabled = editedPlant.name.isNotBlank()
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            } else {
                IconButton(onClick = { isEditing = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { uiStateViewModel.showBackButton(false) }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column {
            PlantCardMax(
                plantWithPhotos = PlantWithPhotos(plant = editedPlant, photos = editedPhotos),
                editable = isEditing,
                onValueChange = { updatedPlant -> editedPlant = updatedPlant },
                onPhotosChanged = { updatedPhotos ->
                    editedPhotos = updatedPhotos
                }
            )

            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        editedPlant = selectedPlantWithPhotos.plant
                        editedPhotos = selectedPlantWithPhotos.photos
                        isEditing = false
                        uiStateViewModel.setDrawerTitle(selectedPlantWithPhotos.plant.name.ifBlank { "Plant" })
                    }) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updatePlant(editedPlant, editedPhotos)
                            isEditing = false
                            uiStateViewModel.setDrawerTitle(editedPlant.name.ifBlank { "Plant" })
                        },
                        enabled = editedPlant.name.isNotBlank()
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}

