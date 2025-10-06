package com.example.myplants.ui.componets.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.models.Genus
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card_fields.CardTextField
import com.example.myplants.ui.theme.GreenLight
import kotlin.collections.forEach

@Composable
fun GenusCardMain(
    genus: Genus,
    plants: List<PlantWithPhotos>,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    onNavigateToGenusDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

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
                    .clickable { expanded = !expanded }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardTextField(
                    label = stringResource(R.string.plant_genus),
                    value = genus.main.genus,
                    onValueChange = { },
                    editable = false
                )
                IconButton(onClick = {
                    onNavigateToGenusDetail(genus.id)
                }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Show detail")
                }
            }

            // Содержимое при раскрытии
            if (expanded) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plants.forEach { plantWithPhotos ->
                        PlantCardMain(
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
