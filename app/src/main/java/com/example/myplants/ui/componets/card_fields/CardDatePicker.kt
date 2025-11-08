package com.example.myplants.ui.componets.card_fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myplants.ui.componets.card_fields.common.AppColors
import com.example.myplants.ui.componets.card_fields.common.CardText
import com.example.myplants.ui.theme.MyPlantsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDatePicker(
    label: String,
    date: String?,
    editable: Boolean,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }

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


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CardDatePickerPreview_WithDate() {
    MyPlantsTheme {
        CardDatePicker(
            label = "Дата посадки",
            date = "15-01-2024",
            editable = true,
            onDateSelected = { /* preview logic */ }
        )
    }
}
