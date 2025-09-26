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
import androidx.navigation.NavHostController
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card.CardDeleteButton
import com.example.myplants.ui.componets.plant_card.PlantCardMax
import com.example.myplants.ui.viewmodels.PlantDetailViewModel
import com.example.myplants.ui.viewmodels.PlantDetailViewModelFactory
import com.example.myplants.ui.viewmodels.UiStateViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantDetailScreen(
    plantId: Long,
    navController: NavHostController,
    uiStateViewModel: UiStateViewModel? = null,
    repository: PlantRepositoryInterface,
    modifier: Modifier = Modifier
) {
    val viewModel: PlantDetailViewModel = viewModel(
        factory = PlantDetailViewModelFactory(plantId, repository)
    )

    val uiStateViewModel: UiStateViewModel = uiStateViewModel ?: viewModel<UiStateViewModel>()

    val plantWithPhotos by viewModel.plantWithPhotos.observeAsState()
    val editedPlant by viewModel.editedPlant.observeAsState()
    val editedPhotos by viewModel.editedPhotos.observeAsState(emptyList())

    if (plantWithPhotos == null || editedPlant == null) {
        Text("Plant not found", modifier = modifier.padding(16.dp))
        return
    }

    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(isEditing, editedPlant) {
        val title = if (isEditing) "Edit: ${editedPlant?.name}" else editedPlant?.name!!.ifBlank { "Plant" }
        uiStateViewModel.setDrawerTitle(title)
        uiStateViewModel.showBackButton(true)

        uiStateViewModel.setTopBarActions {
            if (isEditing) {
                IconButton(onClick = {
                    viewModel.resetChanges()
                    isEditing = false
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                IconButton(
                    onClick = {
                        viewModel.saveChanges()
                        isEditing = false
                        uiStateViewModel.setDrawerTitle(editedPlant?.name!!.ifBlank { "Plant" })
                    },
                    enabled = editedPlant?.name?.isNotBlank() == true
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
                plantWithPhotos = PlantWithPhotos(plant = editedPlant!!, photos = editedPhotos),
                editable = isEditing,
                onValueChange = { updatedPlant -> viewModel.updateEditedPlant(updatedPlant) },
                onPhotosChanged = { updatedPhotos -> viewModel.updateEditedPhotos(updatedPhotos) }
            )

            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        viewModel.resetChanges()
                        isEditing = false
                        uiStateViewModel.setDrawerTitle(plantWithPhotos!!.plant.name.ifBlank { "Plant" })
                    }) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.saveChanges()
                            isEditing = false
                            uiStateViewModel.setDrawerTitle(editedPlant?.name!!.ifBlank { "Plant" })
                        },
                        enabled = editedPlant?.name?.isNotBlank() == true
                    ) {
                        Text("Save Changes")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CardDeleteButton(
                onDeleteConfirmed = {
                    viewModel.deletePlant { navController.popBackStack() }
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
