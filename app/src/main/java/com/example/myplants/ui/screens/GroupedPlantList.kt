package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Genus
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.common.cards.GenusCardMain
import com.example.myplants.ui.viewmodels.SearchSortViewModel

@Composable
fun GroupedPlantList(
    plants: List<PlantWithPhotos>,
    genusMap: Map<String, Genus>,
    searchSortViewModel: SearchSortViewModel,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    onNavigateToGenusDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val query by searchSortViewModel.query.collectAsState()
    val sortType by searchSortViewModel.sortType.collectAsState()

    val filteredPlants = remember(query, sortType, plants) {
        searchSortViewModel.apply(plants)
    }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        filteredPlants.groupBy { it.plant.main.genus }.entries.forEachIndexed { index, (genusName, genusPlants) ->
            item(key = genusName) {
                val genus = genusMap[genusName]
                if (genus != null) {
                    GenusCardMain(
                        genus = genus,
                        plants = genusPlants,
                        onPlantClick = onPlantClick,
                        onToggleFavorite = onToggleFavorite,
                        onToggleWishlist = onToggleWishlist,
                        onNavigateToGenusDetail = onNavigateToGenusDetail,
                        modifier = Modifier.padding(top = if (index == 0) 0.dp else 12.dp)
                    )
                }
            }
        }
    }
}
