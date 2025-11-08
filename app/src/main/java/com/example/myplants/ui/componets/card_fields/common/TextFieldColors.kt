package com.example.myplants.ui.componets.card_fields.common

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.myplants.ui.theme.LightGray
import com.example.myplants.ui.theme.White

object AppColors {
    /**
     * Возвращает набор стандартных цветов для TextField
     */
    @Composable
    fun textFieldColors(): TextFieldColors =
        TextFieldDefaults.colors(
            disabledContainerColor = LightGray,
            unfocusedContainerColor = White,
            focusedContainerColor = White
        )
}