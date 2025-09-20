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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlantDetail(
    plantId: Long,
    viewModel: PlantsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    uiStateViewModelParam: UiStateViewModel? = null
) {
    val uiStateViewModel: UiStateViewModel = uiStateViewModelParam ?: viewModel()
    val selectedPlant = viewModel.getPlantById(plantId)

    // Состояние для режима редактирования
    var isEditing by remember { mutableStateOf(false) }
    // Локальная копия растения для редактирования
    var editedPlant by remember { mutableStateOf(selectedPlant ?: Plant(name = "", species = "")) }

    LaunchedEffect(selectedPlant?.id) {
        selectedPlant?.let { plant ->
            uiStateViewModel.setDrawerTitle(plant.name ?: "Plant")
            editedPlant = plant // Обновляем локальную копию при изменении выбранного растения
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditing) "Edit Plant" else selectedPlant.name ?: "Plant")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (isEditing) {
                        // Кнопки в режиме редактирования
                        IconButton(
                            onClick = {
                                // Отмена редактирования
                                editedPlant = selectedPlant
                                isEditing = false
                            }
                        ) {
                            Icon(Icons.Default.ExitToApp, "Cancel")
                        }
                        IconButton(
                            onClick = {
                                // Сохранение изменений
                                viewModel.updatePlant(editedPlant)
                                isEditing = false
                            },
                            enabled = editedPlant.name.isNotBlank() // Валидация
                        ) {
                            Icon(Icons.Default.Star, "Save")
                        }
                    } else {
                        // Кнопка редактирования в режиме просмотра
                        IconButton(
                            onClick = { isEditing = true }
                        ) {
                            Icon(Icons.Default.Edit, "Edit")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                PlantCardMax(
                    plant = editedPlant,
                    editable = isEditing,
                    onValueChange = { updatedPlant ->
                        editedPlant = updatedPlant
                    }
                )

                // Кнопки внизу для больших экранов
                if (isEditing) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                editedPlant = selectedPlant
                                isEditing = false
                            }
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                viewModel.updatePlant(editedPlant)
                                isEditing = false
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
}