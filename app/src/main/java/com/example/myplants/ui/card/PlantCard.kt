package com.example.myplants.ui.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.ui.theme.GreenLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PlantCard(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenLight
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LabeledTextField(
                label = "Название",
                value = plant.name,
                editable = editable,
                onValueChange = { onValueChange(plant.copy(name = it)) }
            )

            LabeledTextField(
                label = "Вид",
                value = plant.species,
                editable = editable,
                onValueChange = { onValueChange(plant.copy(species = it)) }
            )

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    LabeledTextField(
                        label = "Подвид",
                        value = plant.subSpecies ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(subSpecies = it)) }
                    )

                    LabeledTextField(
                        label = "Освещение",
                        value = plant.lighting ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(lighting = it)) }
                    )

                    LabeledTextField(
                        label = "Цветение",
                        value = plant.bloom ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(bloom = it)) }
                    )

                    LabeledTextField(
                        label = "Состав почвы",
                        value = plant.soilComposition ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(soilComposition = it)) }
                    )

                    LabeledTextField(
                        label = "Пересадка",
                        value = plant.transfer ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(transfer = it)) }
                    )

                    LabeledTextField(
                        label = "Температура",
                        value = plant.temperature ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(temperature = it)) }
                    )

                    LabeledTextField(
                        label = "Влажность воздуха",
                        value = plant.airHumidity ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(airHumidity = it)) }
                    )

                    LabeledTextField(
                        label = "Период покоя",
                        value = plant.restPeriod ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(restPeriod = it)) }
                    )

                    LabeledTextField(
                        label = "Размножение",
                        value = plant.reproduction ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(reproduction = it)) }
                    )

                    LabeledTextField(
                        label = "Вредители",
                        value = plant.pests ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(pests = it)) }
                    )

                    LabeledTextField(
                        label = "Болезни",
                        value = plant.diseases ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(diseases = it)) }
                    )

                    LabeledTextField(
                        label = "Возраст",
                        value = plant.firstLanding ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(firstLanding = it)) }
                    )

                    // Требования к свету
                    LabeledDropdown(
                        label = "Требования к свету",
                        items = listOf("низкое", "среднее", "высокое"),
                        selectedItem = plant.sunlightRequirement ?: "",
                        editable = editable,
                        onItemSelected = { onValueChange(plant.copy(sunlightRequirement = it.ifEmpty { null })) }
                    )

                    LabeledCheckbox(
                        label = "Ядовитость",
                        checked = plant.isToxic ?: false,
                        editable = editable,
                        onCheckedChange = { onValueChange(plant.copy(isToxic = it)) }
                    )

                    LabeledTextField(
                        label = "О растении",
                        value = plant.aboutThePlant ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(aboutThePlant = it)) }
                    )

                    LabeledTextField(
                        label = "Полив",
                        value = plant.watering ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(watering = it)) }
                    )

                    LabeledNumberField(
                        label = "Частота полива (в днях)",
                        value = plant.wateringFrequency?.toString() ?: "",
                        editable = editable,
                        onValueChange = {
                            val num = it.toIntOrNull()
                            onValueChange(plant.copy(wateringFrequency = num))
                        }
                    )

                    LabeledTextField(
                        label = "Дата последнего полива (в формате \"yyyy-MM-dd\")",
                        value = plant.lastWateringDate ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(lastWateringDate = it)) }
                    )


                    LabeledTextField(
                        label = "Подкормки",
                        value = plant.fertilizer ?: "",
                        editable = editable,
                        onValueChange = { onValueChange(plant.copy(fertilizer = it)) }
                    )

                    LabeledNumberField(
                        label = "Частота удобрения (месяцы)",
                        value = plant.fertilizationFrequency?.toString() ?: "",
                        editable = editable,
                        onValueChange = {
                            val num = it.toIntOrNull()
                            onValueChange(plant.copy(fertilizationFrequency = num))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit
) {
    Column {
        CardText(label)
        CardTextField(
            value, editable, onValueChange
        )
    }
}

@Composable
private fun LabeledNumberField(
    label: String,
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit
) {
    Column {
        CardText(label)
        CardTextField(
            value, editable,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                    onValueChange(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
private fun LabeledDropdown(
    label: String,
    items: List<String>,
    selectedItem: String,
    editable: Boolean,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        CardText(label)
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                enabled = editable,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = editable) { expanded = true },
                colors = AppColors.textFieldColors(),
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LabeledCheckbox(
    label: String,
    checked: Boolean,
    editable: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CardText(
            label,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = if (editable) onCheckedChange else null,
            enabled = editable
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledDatePicker(
    label: String,
    date: String?,
    editable: Boolean,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Column {
        CardText(label)
        TextField(
            value = date ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = editable,
            trailingIcon = {
                Icon(Icons.Default.Menu, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = editable) { showDatePicker = true },
            colors = AppColors.textFieldColors()
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val formattedDate = dateFormatter.format(Date(it))
                            onDateSelected(formattedDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}