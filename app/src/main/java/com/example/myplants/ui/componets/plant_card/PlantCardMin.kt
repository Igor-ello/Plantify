package com.example.myplants.ui.componets.plant_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myplants.R
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card.CardIconFavourite
import com.example.myplants.ui.componets.card.CardIconWishlist
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
    // Храним часто используемые значения в remember, чтобы уменьшить перерасчёты
    val plant = remember(plantWithPhotos) { plantWithPhotos.plant }
    val mainPhotoUri = remember(plantWithPhotos.photos) {
        plantWithPhotos.photos.firstOrNull { it.isPrimary }?.uri
    }

    // Локальные мемоизированные обработчики — предотвращают создание новых лямбд на каждую recomposition
    val onClickMemo = remember(plant.id) { { onClick(plant) } }
    val onToggleFavoriteMemo = remember(plant.id) {
        { plant: PlantWithPhotos -> onToggleFavorite(plant) }
    }
    val onToggleWishlistMemo = remember(plant.id) {
        { plant: PlantWithPhotos -> onToggleWishlist(plant) }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClickMemo),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Фото карточки — асинхронно через Coil, с фиксированной высотой для стабильного layout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                if (mainPhotoUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(mainPhotoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "${plant.name} photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        error = painterResource(id = R.drawable.placeholder)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "placeholder",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Ряд кнопок
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Передаём мемоизированные обработчики
                    CardIconFavourite(
                        plantWithPhotos = plantWithPhotos,
                        onToggleFavorite = onToggleFavoriteMemo
                    )
                    CardIconWishlist(
                        plantWithPhotos = plantWithPhotos,
                        onToggleWishlist = onToggleWishlistMemo
                    )
                }

                IconButton(onClick = { /* пока пусто */ }) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Timer"
                    )
                }
            }

            // Основная информация о растении
            PlantBasicContent(plant, editable, onValueChange)
        }
    }
}
