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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.cards.common.CardDeleteButton
import com.example.myplants.ui.componets.cards.plants.PlantCardFull
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlantDetailScreen(
    viewModel: PlantDetailViewModel,
    navController: NavHostController,
    uiStateViewModel: TopBarStateViewModel? = null,
    modifier: Modifier = Modifier
) {
    val uiStateViewModel: TopBarStateViewModel = uiStateViewModel ?: viewModel<TopBarStateViewModel>()

    val plantWithPhotos by viewModel.plantWithPhotos.observeAsState()
    val editedPlant by viewModel.editedPlant.observeAsState()
    val editedPhotos by viewModel.editedPhotos.observeAsState(emptyList())

    if (plantWithPhotos == null || editedPlant == null) {
        Text("Plant not found", modifier = modifier.padding(16.dp))
        return
    }

    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(isEditing, editedPlant) {
        val title =
            if (isEditing) "Edit: ${editedPlant?.main?.species}"
            else editedPlant?.main?.species!!.ifBlank { "Plant" }
        uiStateViewModel.setDrawerTitle(title)
        uiStateViewModel.showBackButton(true)

        uiStateViewModel.setTopBarActions {
            if (isEditing) {
                IconButton(onClick = {
                    viewModel.resetChanges()
                    isEditing = false
                }) {
                    // TODO
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                IconButton(
                    onClick = {
                        viewModel.saveChanges()
                        isEditing = false
                        uiStateViewModel.setDrawerTitle(editedPlant?.main?.species!!.ifBlank { "Plant" })
                    },
                    enabled = editedPlant?.main?.species?.isNotBlank() == true
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
            PlantCardFull(
                plantWithPhotos = PlantWithPhotos(plant = editedPlant!!, photos = editedPhotos),
                editable = isEditing,
                onValueChange = { updatedPlant -> viewModel.updateEditedPlant(updatedPlant as Plant) },
                onPhotosChanged = { updatedPhotos -> viewModel.updateEditedPhotos(updatedPhotos) }
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
                            uiStateViewModel
                                .setDrawerTitle(plantWithPhotos!!.plant.main.species.ifBlank { "Plant" })
                        },
                        text = stringResource(R.string.button_cancel)
                    )
                    AppButton(
                        onClick = {
                            viewModel.saveChanges()
                            isEditing = false
                            uiStateViewModel.setDrawerTitle(editedPlant?.main?.species!!.ifBlank { "Plant" })
                        },
                        enabled = editedPlant?.main?.species?.isNotBlank() == true,
                        text = stringResource(R.string.button_save)
                    )
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
