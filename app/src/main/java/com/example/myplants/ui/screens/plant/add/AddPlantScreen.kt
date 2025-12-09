package com.example.myplants.ui.screens.plant.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.cards.plants.PlantCardEventHandler
import com.example.myplants.ui.componets.cards.plants.PlantCardFull
import com.example.myplants.ui.componets.cards.plants.PlantCardState
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel


@Composable
fun AddPlantScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AddPlantViewModel = hiltViewModel()
    SetupTopBar()

    val newPlant by viewModel.newPlant.collectAsStateWithLifecycle()
    val newPhotos by viewModel.newPlantPhotos.collectAsStateWithLifecycle()
    val newPlantWithPhotos = PlantWithPhotos(plant = newPlant, photos = newPhotos)

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            // TODO page Modifier
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val events = PlantCardEventHandler(
                onValueChange = { updatedPlant ->
                    viewModel.updateNewPlantFields(updatedPlant as Plant)
                },
                onPhotosChanged = { updatedPhotos ->
                    viewModel.updateNewPlantPhotos(updatedPhotos)
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
            val state = PlantCardState(
                plantWithPhotos = newPlantWithPhotos,
                editable = true
            )
            PlantCardFull(
                state = state,
                eventHandler = events
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppButton(
                    onClick = { onClose() },
                    text = stringResource(R.string.button_cancel)
                )

                AppButton(
                    onClick = { viewModel.saveNewPlant(onClose) },
                    enabled = newPlant.main.genus.isNotBlank() && newPlant.main.species.isNotBlank(),
                    text = stringResource(R.string.button_save)
                )
            }
        }
    }
}

@Composable
private fun SetupTopBar() {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val title = stringResource(R.string.screen_add_plant)

    LaunchedEffect(Unit) {
        topBarState.setTitle(title)
    }
}