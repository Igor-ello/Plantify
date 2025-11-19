package com.example.myplants.ui.componets.common.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.theme.GenusCardColor
import com.example.myplants.ui.theme.MyPlantsTheme

@Composable
fun GenusCardMain(
    genus: Genus,
    plants: List<PlantWithPhotos>,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    onNavigateToGenusDetail: (Long) -> Unit,
    initiallyExpanded: Boolean = false,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = GenusCardColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleLarge(
                    text = genus.main.genus,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { onNavigateToGenusDetail(genus.id) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Show detail")
                }
            }

            val scrollState = rememberScrollState()
            // Содержимое при раскрытии
            if (expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .horizontalScroll(scrollState)
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

@Preview(showBackground = true)
@Composable
private fun GenusCardMainPreview() {
    MyPlantsTheme {
        val genus = Genus(
            id = 1L,
            main = MainInfo(species = "", genus = "Фикус")
        )

        val plants = listOf(
            PlantWithPhotos(
                plant = Plant(
                    id = 1L, genusId = 1L, main = MainInfo(species = "Фикус Бенджамина", genus = "Фикус")
                ),
                photos = emptyList()
            ),
            PlantWithPhotos(
                plant = Plant(
                    id = 2L, genusId = 1L, main = MainInfo(species = "Фикус Лирата", genus = "Фикус")
                ),
                photos = emptyList()
            )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            GenusCardMain(
                genus = genus,
                plants = plants,
                onPlantClick = {},
                onToggleFavorite = {},
                onToggleWishlist = {},
                onNavigateToGenusDetail = {},
                initiallyExpanded = true
            )
        }
    }
}
