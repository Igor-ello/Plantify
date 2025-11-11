package com.example.myplants.ui.componets.common.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantEntityInterface
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.ui.componets.card_fields.CardBasicContent
import com.example.myplants.ui.componets.card_fields.CardIconFavourite
import com.example.myplants.ui.componets.card_fields.CardIconWishlist
import com.example.myplants.ui.theme.CardColors
import com.example.myplants.ui.theme.MyPlantsTheme

@Composable
fun PlantCardMain(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onClick: (Plant) -> Unit,
    onValueChange: (PlantEntityInterface) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    modifier: Modifier = Modifier
) {
    val plant = remember(plantWithPhotos) { plantWithPhotos.plant }
    val mainPhotoUri = remember(plantWithPhotos.photos) {
        plantWithPhotos.photos.firstOrNull { it.isPrimary }?.uri
    }

    // Случайный цвет для карточки
    val backgroundColor = remember { CardColors.colors.random() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(plant) },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Фото карточки
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 300.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                if (mainPhotoUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(mainPhotoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "${plant.main.species} photo",
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
                    CardIconFavourite(
                        plantWithPhotos = plantWithPhotos,
                        onToggleFavorite = { onToggleFavorite(plantWithPhotos) }
                    )
                    CardIconWishlist(
                        plantWithPhotos = plantWithPhotos,
                        onToggleWishlist = { onToggleWishlist(plantWithPhotos) }
                    )
                }

                IconButton(onClick = { /* пока пусто */ }) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Timer"
                    )
                }
            }

            CardBasicContent(plant, editable, onValueChange)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlantCardMainPreview() {
    MyPlantsTheme {
        val plantWithPhotosList = listOf(
            PlantWithPhotos(
                plant = Plant(
                    id = 1L,
                    genusId = 1L,
                    main = MainInfo(species = "Фикус Бенджамина", genus = "Фикус")
                ),
                photos = emptyList()
            ),
            PlantWithPhotos(
                plant = Plant(
                    id = 2L,
                    genusId = 1L,
                    main = MainInfo(species = "Фикус Лирата", genus = "Фикус")
                ),
                photos = emptyList()
            )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            plantWithPhotosList.forEach { plantWithPhotos ->
                var editable by remember { mutableStateOf(true) }

                PlantCardMain(
                    plantWithPhotos = plantWithPhotos,
                    editable = editable,
                    onClick = {},
                    onValueChange = {},
                    onToggleFavorite = {},
                    onToggleWishlist = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
