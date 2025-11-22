package com.example.myplants.ui.componets.cards.plants

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
    val backgroundColor by remember(state.plantWithPhotos.plant.id) {
        derivedStateOf { CardColors.colors.random() }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { eventHandler.onClick(state.plantWithPhotos) },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Photo section
            PlantCardImage(state.mainPhotoUri)

            // Actions row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardIconFavourite(
                        plantWithPhotos = state.plantWithPhotos,
                        onToggleFavorite = remember(state.plantWithPhotos.plant.id) {
                            { eventHandler.onToggleFavorite(state.plantWithPhotos) }
                        }
                    )
                    CardIconWishlist(
                        plantWithPhotos = state.plantWithPhotos,
                        onToggleWishlist = remember(state.plantWithPhotos.plant.id) {
                            { eventHandler.onToggleWishlist(state.plantWithPhotos) }
                        }
                    )
                }

                IconButton(onClick = { /* пока пусто */ }) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Timer"
                    )
                }
            }

            // Basic content
            CardBasicContent(state.plant, state.editable, eventHandler.onValueChange)
        }
    }
}

@Composable
private fun PlantCardImage(mainPhotoUri: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (mainPhotoUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mainPhotoUri)
                    .crossfade(true)
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
