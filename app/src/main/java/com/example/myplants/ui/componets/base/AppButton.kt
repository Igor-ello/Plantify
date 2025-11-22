package com.example.myplants.ui.componets.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.core.ui.theme.ButtonColors
import com.example.myplants.core.ui.theme.MyPlantsTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    primary: Boolean = true
) {
    val containerColor = if (primary) ButtonColors.primary else ButtonColors.secondary
    val disabledContainerColor = containerColor.copy(alpha = 0.4f)
    val contentColor = MaterialTheme.colorScheme.onPrimary
    val disabledContentColor = contentColor.copy(alpha = 0.6f)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    AppButton(
        text = text,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        primary = true
    )
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    AppButton(
        text = text,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        primary = false
    )
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    MyPlantsTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(text = "Добавить растение", onClick = {})
            SecondaryButton(text = "Удалить растение", onClick = {})
        }
    }
}
