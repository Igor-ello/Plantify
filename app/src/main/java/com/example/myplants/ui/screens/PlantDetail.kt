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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myplants.models.Plant
import com.example.myplants.plants.PlantsViewModel
import com.example.myplants.ui.navigation.UiStateViewModel
import com.example.myplants.ui.plant_card.PlantCardMax


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantDetail(
    plantId: Long,
    viewModel: PlantsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    uiStateViewModel: UiStateViewModel? = null
) {
    // Явно запрашиваем UiStateViewModel, если не передали сверху
    val uiStateViewModel: UiStateViewModel = uiStateViewModel ?: viewModel<UiStateViewModel>()
    val selectedPlant = viewModel.getPlantById(plantId)

    var isEditing by remember { mutableStateOf(false) }
    var editedPlant by remember { mutableStateOf(selectedPlant ?: Plant(name = "", species = "")) }

    // Обновляем title, back button и action-ы
    LaunchedEffect(selectedPlant?.id, isEditing) {
        val title = if (isEditing) "Edit: ${selectedPlant?.name ?: ""}" else (selectedPlant?.name ?: "Plant")
        uiStateViewModel.setDrawerTitle(title)
        uiStateViewModel.showBackButton(true)

        uiStateViewModel.setTopBarActions {
            if (isEditing) {
                IconButton(onClick = {
                    editedPlant = selectedPlant ?: editedPlant
                    isEditing = false
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                IconButton(onClick = {
                    viewModel.updatePlant(editedPlant)
                    isEditing = false
                }, enabled = editedPlant.name.isNotBlank()) {
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
        onDispose {
            uiStateViewModel.resetDrawerTitle()
            uiStateViewModel.clearTopBarActions()
            uiStateViewModel.showBackButton(false)
        }
    }

    if (selectedPlant == null) {
        Text("Plant not found")
        return
    }

    // Контент экрана — без Scaffold (TopAppBar общий)
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column {
            PlantCardMax(
                plant = editedPlant,
                editable = isEditing,
                onValueChange = { updatedPlant -> editedPlant = updatedPlant }
            )

            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = { editedPlant = selectedPlant; isEditing = false }) {
                        Text("Cancel")
                    }
                    Button(onClick = { viewModel.updatePlant(editedPlant); isEditing = false }, enabled = editedPlant.name.isNotBlank()) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}
