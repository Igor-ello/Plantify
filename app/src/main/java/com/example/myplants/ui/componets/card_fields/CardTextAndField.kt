package com.example.myplants.ui.componets.card_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.ui.componets.common.CardText
import com.example.myplants.ui.componets.common.CardTextField

@Composable
fun CardTextAndField(
    label: String,
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CardText(label)
        CardTextField(
            value, editable, onValueChange
        )
    }
}