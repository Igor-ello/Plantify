package com.example.myplants.ui.componets.common.cards.genus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.componets.common.cards.plants.PlantCardEventHandler
import com.example.myplants.ui.componets.common.cards.plants.PlantCardMain
import com.example.myplants.ui.componets.common.cards.plants.PlantCardState
import com.example.myplants.ui.theme.GenusCardColor
import com.example.myplants.ui.theme.MyPlantsTheme

@Composable
fun GenusCardMain(
    state: GenusCardState,
    eventHandler: GenusCardEventHandler,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = GenusCardColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { eventHandler.onToggleExpanded() }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleLarge(
                    text = state.genus.main.genus,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { eventHandler.onNavigateToGenusDetail(state.genus.id) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Preview, contentDescription = "Show detail")
                }
            }

            if (state.isExpanded) {
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .horizontalScroll(scrollState)
                ) {
                    state.plantWithPhotos.forEach { it ->
                        val plantState = PlantCardState(
                            plantWithPhotos = it,
                            editable = false
                        )

                        val plantEventHandler = PlantCardEventHandler(
                            onClick = { eventHandler.onPlantClick(it) },
                            onToggleFavorite = { eventHandler.onToggleFavorite(it) },
                            onToggleWishlist = { eventHandler.onToggleWishlist(it) }
                        )

                        PlantCardMain(
                            state = plantState,
                            eventHandler = plantEventHandler,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp)
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
                    id = 1L, genusId = 1L,
                    main = MainInfo(species = "Фикус Бенджамина", genus = "Фикус")
                ),
                photos = emptyList()
            ),
            PlantWithPhotos(
                plant = Plant(
                    id = 2L, genusId = 1L,
                    main = MainInfo(species = "Фикус Лирата", genus = "Фикус")
                ),
                photos = emptyList()
            )
        )

        val state = GenusCardState(
            genus = genus,
            plantWithPhotos = plants,
            isExpanded = true
        )

        val eventHandler = GenusCardEventHandler(
            onPlantClick = { /* preview */ },
            onToggleFavorite = { /* preview */ },
            onToggleWishlist = { /* preview */ },
            onNavigateToGenusDetail = { /* preview */ },
            onToggleExpanded = { /* preview */ }
        )

        Column(modifier = Modifier.padding(16.dp)) {
            GenusCardMain(
                state = state,
                eventHandler = eventHandler
            )
        }
    }
}
