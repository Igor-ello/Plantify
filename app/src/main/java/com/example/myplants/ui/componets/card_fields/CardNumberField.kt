package com.example.myplants.ui.componets.card_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.example.myplants.ui.componets.card_fields.common.CardText
import com.example.myplants.ui.componets.card_fields.common.CardTextField

@Composable
fun CardNumberField(
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