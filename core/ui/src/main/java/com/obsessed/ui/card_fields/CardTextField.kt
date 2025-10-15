package com.obsessed.ui.card_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.myplants.ui.componets.common.CardText
import com.example.myplants.ui.componets.common.CardTextField

@Composable
fun CardTextField(
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