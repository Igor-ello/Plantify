package com.greencore.plantify.ui.componets.base

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greencore.plantify.core.ui.theme.ButtonColors
import com.greencore.plantify.core.ui.theme.AppTheme

@Composable
fun AppCheckbox(
    label: String,
    checked: Boolean,
    editable: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        BodyMedium(
            text = label,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = if (editable) onCheckedChange else null,
            enabled = editable,
            colors = CheckboxDefaults.colors(
                checkedColor = ButtonColors.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                disabledCheckedColor = ButtonColors.primary.copy(alpha = 0.4f),
                disabledUncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppCheckboxPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppCheckbox(
                label = "Показать избранные",
                checked = true,
                onCheckedChange = {}
            )
            AppCheckbox(
                label = "Показать избранные",
                checked = false,
                onCheckedChange = {}
            )
        }
    }
}
