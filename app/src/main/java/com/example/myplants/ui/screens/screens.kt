package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.plants.PlantsViewModel

@Composable
fun FavoritesScreen(
    viewModel: PlantsViewModel,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    // Реализуйте экран избранного
    Text("Favorites Screen", modifier = modifier.padding(16.dp))
}

@Composable
fun AddPlantScreen(
    viewModel: PlantsViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Реализуйте экран добавления растения
    Column(modifier = modifier.padding(16.dp)) {
        Text("Add Plant Screen")
        Row {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onSave) {
                Text("Save")
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Settings Screen")
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun HelpScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Help Screen")
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}