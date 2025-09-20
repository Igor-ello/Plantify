package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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