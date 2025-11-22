package com.example.myplants.ui.componets.cards.plants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.ui.componets.cards.common.CardBasicContent
import com.example.myplants.ui.componets.cards.common.CardIconFavourite
import com.example.myplants.ui.componets.cards.common.CardIconWishlist
import com.example.myplants.core.ui.theme.CardColors
import com.example.myplants.core.ui.theme.MyPlantsTheme

@Composable
fun PlantCardMain(
    state: PlantCardState,
    eventHandler: PlantCardEventHandler,
    modifier: Modifier = Modifier
) {
    val cardContent by remember(state, eventHandler) {
        derivedStateOf {
            @Composable {
                val backgroundColor by remember(state.plantWithPhotos.plant.id) {
                    derivedStateOf { CardColors.colors.random() }
                }

                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Отключаем анимацию ripple для производительности
                        ) { eventHandler.onClick(state.plantWithPhotos) },
                    shape = CardDefaults.shape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Photo section - фиксированная высота
                        PlantCardImage(state.mainPhotoUri)

                        // Actions row
                        PlantCardActions(
                            plantWithPhotos = state.plantWithPhotos,
                            eventHandler = eventHandler
                        )

                        // Basic content
                        CardBasicContent(state.plant, state.editable, eventHandler.onValueChange)
                    }
                }
            }
        }
    }

    cardContent()
}

@Composable
private fun PlantCardActions(
    plantWithPhotos: PlantWithPhotos,
    eventHandler: PlantCardEventHandler,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ОПТИМИЗАЦИЯ: стабильные ссылки на колбэки
            val onToggleFavorite = remember(plantWithPhotos.plant.id) {
                { eventHandler.onToggleFavorite(plantWithPhotos) }
            }
            val onToggleWishlist = remember(plantWithPhotos.plant.id) {
                { eventHandler.onToggleWishlist(plantWithPhotos) }
            }

            CardIconFavourite(
                onToggleFavorite = onToggleFavorite,
                plantWithPhotos = plantWithPhotos
            )
            CardIconWishlist(
                onToggleWishlist = onToggleWishlist,
                plantWithPhotos = plantWithPhotos
            )
        }

        IconButton(
            onClick = { /* пока пусто */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Timer",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun PlantCardImage(mainPhotoUri: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Уменьшаем высоту для производительности
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (mainPhotoUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mainPhotoUri)
                    .crossfade(false) // Отключаем crossfade для производительности
                    .size(Size(300, 150)) // Фиксированный размер для кэширования
                    .build(),
                contentDescription = null,
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
}

@Preview(showBackground = true)
@Composable
private fun PlantCardMainPreview() {
    MyPlantsTheme {
        val plantWithPhotos = PlantWithPhotos(
            plant = Plant(
                id = 1L,
                genusId = 1L,
                main = MainInfo(species = "Фикус Бенджамина", genus = "Фикус")
            ),
            photos = emptyList()
        )

        val state = PlantCardState(
            plantWithPhotos = plantWithPhotos,
            editable = false
        )

        val eventHandler = PlantCardEventHandler(
            onClick = { /* preview */ },
            onToggleFavorite = { /* preview */ },
            onToggleWishlist = { /* preview */ }
        )

        PlantCardMain(
            state = state,
            eventHandler = eventHandler,
            modifier = Modifier.padding(16.dp)
        )
    }
}
