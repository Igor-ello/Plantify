package com.example.myplants.ui.screens.plant.detail

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.cards.common.CardDeleteButton
import com.example.myplants.ui.componets.cards.plants.PlantCardEventHandler
import com.example.myplants.ui.componets.cards.plants.PlantCardFull
import com.example.myplants.ui.componets.topbar.TopBarAction
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantDetailScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: PlantDetailViewModel = hiltViewModel()
    val topBarState: TopBarStateViewModel = hiltViewModel()

    val editedPlant by viewModel.editedPlant.collectAsStateWithLifecycle()
    val editedPhotos by viewModel.editedPhotos.collectAsStateWithLifecycle()

    if (editedPlant == null) {
        Text("Plant not found", modifier = modifier.padding(16.dp))
        return
    }

    var isEditing by remember { mutableStateOf(false) }

    // --- Настройка TopBar ---
    LaunchedEffect(isEditing, editedPlant) {
        val title = if (isEditing) "Edit: ${editedPlant?.main?.species}"
        else editedPlant?.main?.species!!.ifBlank { "Plant" }

        topBarState.setTitle(title)
        topBarState.showMenuButton(false)

        if (isEditing) {
            topBarState.actionsManager.set(
                TopBarAction(
                    id = "cancel",
                    icon = { Icon(Icons.Default.Close, "Cancel") },
                    onClick = {
                        viewModel.resetChanges()
                        isEditing = false
                    }
                ),
                TopBarAction(
                    id = "save",
                    icon = { Icon(Icons.Default.Check, "Save") },
                    onClick = {
                        viewModel.saveChanges()
                        isEditing = false
                    }
                )
            )
        } else {
            topBarState.actionsManager.set(
                TopBarAction(
                    id = "edit",
                    icon = { Icon(Icons.Default.Edit, "Edit") },
                    onClick = { isEditing = true }
                )
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose { topBarState.showMenuButton(true) }
    }

    // --- UI экрана ---
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column {
            val events = PlantCardEventHandler(
                onValueChange = { updatedPlant ->
                    viewModel.updateEditedPlantFields(updatedPlant as Plant)
                },
                onPhotosChanged = { updatedPhotos ->
                    viewModel.updateEditedPlantPhotos(updatedPhotos)
                },
                onAddPhoto = { bitmap ->
                    viewModel.addImageFromBitmap(bitmap)
                },
                onReplacePhoto = { index, bitmap ->
                    viewModel.replaceImageAt(index, bitmap)
                },
                onDeletePhoto = { index ->
                    viewModel.removeImageAt(index)
                }
            )
            val plant = editedPlant ?: Plant(id = 0L, genusId = 0L, main = MainInfo(species = "", genus = ""))
            val photos = editedPhotos.filterNotNull()
            PlantCardFull(
                plantWithPhotos = PlantWithPhotos(plant = plant, photos = photos),
                editable = isEditing,
                eventHandler = events
            )

            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppButton(
                        onClick = {
                            viewModel.resetChanges()
                            isEditing = false
                            topBarState.setTitle(editedPlant!!.main.species.ifBlank { "Plant" })
                        },
                        text = stringResource(R.string.button_cancel)
                    )
                    AppButton(
                        onClick = {
                            viewModel.saveChanges()
                            isEditing = false
                            topBarState.setTitle(editedPlant!!.main.species.ifBlank { "Plant" })
                        },
                        enabled = editedPlant?.main?.species?.isNotBlank() == true,
                        text = stringResource(R.string.button_save)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CardDeleteButton(
                onDeleteConfirmed = { viewModel.deletePlant { navController.popBackStack() } },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}