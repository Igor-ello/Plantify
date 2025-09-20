package com.example.myplants.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    uiStateViewModelParam: UiStateViewModel? = null // nullable параметр по умолчанию
) {
    // если uiStateViewModel передали извне — используем его, иначе создаём/получаем Compose-scoped ViewModel
    val uiStateViewModel: UiStateViewModel = uiStateViewModelParam ?: viewModel()

    val selectedPlant = viewModel.getPlantById(plantId)

    LaunchedEffect(selectedPlant?.id) {
        selectedPlant?.let { plant ->
            uiStateViewModel.setDrawerTitle(plant.name ?: "Plant")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            uiStateViewModel.resetDrawerTitle()
        }
    }

    if (selectedPlant == null) {
        Text("Plant not found")
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            PlantCardMax(
                plant = selectedPlant,
                editable = false,
                onValueChange = {}
            )
        }
    }
}