package com.example.myplants.ui.componets.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CardTextField(
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = editable,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = AppColors.textFieldColors(),
        keyboardOptions = keyboardOptions
    )
}