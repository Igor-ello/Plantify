package com.example.myplants.ui.componets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myplants.ui.theme.GreenAdditional

@Composable
fun AppButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String = ""
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(GreenAdditional)
    ) {
        Text(text)
    }
}