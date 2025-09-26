package com.example.myplants.ui.componets.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.myplants.R
import com.example.myplants.ui.theme.White

val poppinsSemiBold = Font(R.font.poppins_semibold)
val PoppinsFontFamily = FontFamily(poppinsSemiBold)

@Composable
fun CardText(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = PoppinsFontFamily),
        color = White,
        modifier = modifier
    )
}