package com.example.myplants.ui.componets.plant_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.theme.GreenLight

@Composable
fun GenusCard(
    genus: String,
    plants: List<PlantWithPhotos>,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    onEditGenus: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf(false) }
    var editedGenus by remember { mutableStateOf(genus) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight.copy(alpha = 0.7f))
    ) {
        Column {
            // Шапка карточки (genus + кнопка редактирования)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded } // клик на шапку раскрывает/сворачивает
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (editing) {
                    TextField(
                        value = editedGenus,
                        onValueChange = { editedGenus = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                    IconButton(onClick = {
                        editing = false
                        onEditGenus(editedGenus)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                } else {
                    Text(
                        text = genus.ifBlank { "Без рода" },
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { editing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit genus")
                    }
                }
            }

            // Содержимое при раскрытии
            if (expanded) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plants.forEach { plantWithPhotos ->
                        PlantCardMin(
                            plantWithPhotos = plantWithPhotos,
                            editable = false,
                            onValueChange = {},
                            onClick = { onPlantClick(plantWithPhotos) },
                            onToggleFavorite = onToggleFavorite,
                            onToggleWishlist = onToggleWishlist
                        )
                    }
                }
            }
        }
    }
}