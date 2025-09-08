package com.example.myplants.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.myplants.ui.custom.CardText
import com.example.myplants.ui.custom.CardTextField

@Composable
fun LabeledTextField(
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