package com.example.myplants.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.ui.theme.LightGray
import com.example.myplants.ui.theme.White

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