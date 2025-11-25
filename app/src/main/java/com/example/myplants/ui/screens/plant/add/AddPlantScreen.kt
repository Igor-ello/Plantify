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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.cards.plants.PlantCardFull
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel


@Composable
fun AddPlantScreen(
    viewModel: AddPlantViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    SetupTopBar()

    val newPlant by viewModel.newPlant.observeAsState(Plant(main = MainInfo(genus = "", species = "")))
    val newPhotos by viewModel.newPlantPhotos.observeAsState(emptyList())
    val newPlantWithPhotos = PlantWithPhotos(plant = newPlant, photos = newPhotos)

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlantCardFull(
                plantWithPhotos = newPlantWithPhotos,
                editable = true,
                onValueChange = { updatedPlant -> viewModel.updateNewPlant(updatedPlant as Plant) },
                onPhotosChanged = { updatedPhotos -> viewModel.updateNewPlantPhotos(updatedPhotos) }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppButton(
                    onClick = {
                        viewModel.clearNewPlant()
                        onCancel()
                    },
                    text = stringResource(R.string.button_cancel)
                )

                AppButton(
                    onClick = { viewModel.saveNewPlant(onSave) },
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