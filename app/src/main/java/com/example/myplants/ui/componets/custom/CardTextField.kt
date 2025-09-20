package com.example.myplants.ui.componets.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

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
        singleLine = false,
        modifier = Modifier.fillMaxWidth(),
        colors = AppColors.textFieldColors(),
        keyboardOptions = keyboardOptions,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Justify // Выравнивание по ширине
        )
    )
}