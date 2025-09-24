package com.example.myplants.ui.plant_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card.CardIconFavourite
import com.example.myplants.ui.componets.card.CardIconWishlist
import com.example.myplants.ui.componets.card.CardPhoto
import com.example.myplants.ui.theme.GreenLight


@Composable
fun PlantCardMin(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onValueChange: (Plant) -> Unit,
    onClick: (Plant) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    modifier: Modifier = Modifier
) {
    val mainPhotoUri = plantWithPhotos.photos.firstOrNull { it.isPrimary }?.uri
    val plant = plantWithPhotos.plant

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(plant) },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CardPhoto(mainPhotoUri)
                CardIconFavourite(
                    plantWithPhotos = plantWithPhotos,
                    onToggleFavorite = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                )
                CardIconWishlist(
                    plantWithPhotos = plantWithPhotos,
                    onToggleWishlist = onToggleWishlist,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
                )
            }

            PlantBasicContent(plant, editable, onValueChange)
        }
    }
}
