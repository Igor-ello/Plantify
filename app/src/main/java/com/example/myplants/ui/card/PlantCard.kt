package com.example.myplants.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.ui.theme.GreenLight
import com.example.myplants.ui.theme.LightGray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PlantCard(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        //shape = RoundedCornerShape(size = 12.dp),
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
            // Название растения
            LabeledTextField(
                label = "Название",
                value = plant.name,
                editable = editable,
                onValueChange = { onValueChange(plant.copy(name = it)) }
            )

            // Вид растения
            LabeledTextField(
                label = "Вид",
                value = plant.species,
                editable = editable,
                onValueChange = { onValueChange(plant.copy(species = it)) }
            )

//            // Размеры
//            LabeledTextField(
//                label = "Размеры",
//                value = plant.dimensions ?: "",
//                editable = editable,
//                onValueChange = { onValueChange(plant.copy(dimensions = it.ifEmpty { null })) }
//            )
//
//            // Частота полива
//            LabeledNumberField(
//                label = "Частота полива (дни)",
//                value = plant.waterFrequency?.toString() ?: "",
//                editable = editable,
//                onValueChange = {
//                    val num = it.toIntOrNull()
//                    onValueChange(plant.copy(waterFrequency = num))
//                }
//            )

            // Требования к свету
            LabeledDropdown(
                label = "Требования к свету",
                items = listOf("низкое", "среднее", "высокое"),
                selectedItem = plant.sunlightRequirement ?: "",
                editable = editable,
                onItemSelected = { onValueChange(plant.copy(sunlightRequirement = it.ifEmpty { null })) }
            )

//            // Уровень влажности
//            LabeledNumberField(
//                label = "Уровень влажности (%)",
//                value = plant.humidityLevel?.toString() ?: "",
//                editable = editable,
//                onValueChange = {
//                    val num = it.toIntOrNull()
//                    onValueChange(plant.copy(humidityLevel = num))
//                }
//            )

            // Ядовитость
            LabeledCheckbox(
                label = "Ядовито для животных",
                checked = plant.isToxic ?: false,
                editable = editable,
                onCheckedChange = { onValueChange(plant.copy(isToxic = it)) }
            )

//            // Дата последнего полива
//            LabeledDatePicker(
//                label = "Дата последнего полива",
//                date = plant.lastWateredDate,
//                editable = editable,
//                onDateSelected = { onValueChange(plant.copy(lastWateredDate = it)) }
//            )

            // Частота удобрения
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