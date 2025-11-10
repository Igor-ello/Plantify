package com.example.myplants.ui.componets.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.ui.theme.MyPlantsTheme

@Composable
fun NamedTextField(
    label: String,
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BodyMedium(text = label)

        AppTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = editable
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NamedTextFieldPreview() {
    MyPlantsTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var value1 by remember { mutableStateOf("") }
            var value2 by remember { mutableStateOf("Роза") }

            NamedTextField(
                label = "Пустое поле",
                value = value1,
                editable = true,
                onValueChange = { value1 = it }
            )

            NamedTextField(
                label = "Заполненное поле",
                value = value2,
                editable = true,
                onValueChange = { value2 = it }
            )

            NamedTextField(
                label = "Недоступное поле",
                value = value2,
                editable = false,
                onValueChange = {}
            )
        }
    }
}