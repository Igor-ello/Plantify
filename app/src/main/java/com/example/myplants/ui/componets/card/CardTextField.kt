package com.example.myplants.ui.componets.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.ui.componets.custom.CardText
import com.example.myplants.ui.componets.custom.CardTextField

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