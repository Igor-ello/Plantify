package com.example.myplants.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.ui.viewmodels.AddPlantViewModel

@Composable
fun AddGroupScreen(
    viewModel: AddPlantViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
//    val newPlant by viewModel.newPlant.observeAsState(Plant(name = "", species = ""))
//    val newPhotos by viewModel.newPlantPhotos.observeAsState(emptyList())
//    val newPlantWithPhotos = PlantWithPhotos(plant = newPlant, photos = newPhotos)
//
//    // Состояния для меню применения
//    var showApplyMenu by remember { mutableStateOf(false) }
//    var selectedField by remember { mutableStateOf("") }
//    var showFieldDropdown by remember { mutableStateOf(false) }
//
//    // Список полей, кроме name, species, subSpecies
//    val fields = remember { newPlant.mutableFields().map { it.name } }
//
//    Box(modifier = modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//
//            // Карточка растения
//            PlantCardMax(
//                plantWithPhotos = newPlantWithPhotos,
//                editable = true,
//                onValueChange = { updatedPlant -> viewModel.updateNewPlant(updatedPlant) },
//                onPhotosChanged = { updatedPhotos -> viewModel.updateNewPlantPhotos(updatedPhotos) }
//            )
//
//            // Dropdown для выбора поля
//            Box {
//                Button(onClick = { showFieldDropdown = true }) {
//                    Text(
//                        text = if (selectedField.isBlank()) "Select field to apply" else selectedField
//                    )
//                }
//                DropdownMenu(
//                    expanded = showFieldDropdown,
//                    onDismissRequest = { showFieldDropdown = false }
//                ) {
//                    fields.forEach { fieldName ->
//                        DropdownMenuItem(
//                            text = { Text(fieldName) },
//                            onClick = {
//                                selectedField = fieldName
//                                showFieldDropdown = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Кнопка применения поля ко всем растениям с таким же названием
//            if (selectedField.isNotBlank()) {
//                Button(onClick = { showApplyMenu = true }) {
//                    Text("Apply field to all with same name")
//                }
//
//                DropdownMenu(
//                    expanded = showApplyMenu,
//                    onDismissRequest = { showApplyMenu = false }
//                ) {
//                    ApplyMode.values().forEach { mode ->
//                        val modeText = when (mode) {
//                            ApplyMode.Overwrite -> "Overwrite all"
//                            ApplyMode.Prepend -> "Add on top"
//                            ApplyMode.Append -> "Add at bottom"
//                        }
//                        DropdownMenuItem(
//                            text = { Text(modeText) },
//                            onClick = {
//                                val fieldValue = newPlant.getFieldValue(selectedField)
//                                viewModel.applyFieldToAllPlants(
//                                    fieldName = selectedField,
//                                    value = fieldValue,
//                                    mode = mode
//                                )
//                                showApplyMenu = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Кнопки Cancel и Save
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Button(onClick = {
//                    viewModel.clearNewPlant()
//                    onCancel()
//                }) {
//                    Text("Cancel")
//                }
//
//                Button(
//                    onClick = { viewModel.saveNewPlant(onSave) },
//                    enabled = newPlant.name.isNotBlank() && newPlant.species.isNotBlank()
//                ) {
//                    Text("Save")
//                }
//            }
//        }
//    }
}
