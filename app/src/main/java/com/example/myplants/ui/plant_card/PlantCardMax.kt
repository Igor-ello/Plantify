package com.example.myplants.ui.plant_card


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card.CardPhoto
import com.example.myplants.ui.theme.GreenLight

@Composable
fun PlantCardMax(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onValueChange: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val photos = plantWithPhotos.photos
            if (photos.isNotEmpty()) {
                // Сортируем: главное фото впереди
                val sortedPhotos = photos.sortedByDescending { it.isPrimary }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(sortedPhotos) { photo ->
                        CardPhoto(photo.uri)
                    }
                }
            } else {
                CardPhoto(null)
            }

            PlantBasicContent(plantWithPhotos.plant, editable, onValueChange)
            PlantDetailContent(plantWithPhotos.plant, editable, onValueChange)
        }
    }
}
