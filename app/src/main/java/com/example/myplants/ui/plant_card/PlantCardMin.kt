package com.example.myplants.ui.plant_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun PlantCardMin(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onValueChange: (Plant) -> Unit,
    onClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    val mainPhotoUri = plantWithPhotos.photos.firstOrNull { it.isPrimary }?.uri

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(plantWithPhotos.plant) },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardPhoto(mainPhotoUri)
            PlantBasicContent(plantWithPhotos.plant, editable, onValueChange)
        }
    }
}
