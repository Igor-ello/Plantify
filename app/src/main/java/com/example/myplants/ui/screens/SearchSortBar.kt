package com.example.myplants.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.ui.viewmodels.SearchSortViewModel
import com.example.myplants.ui.viewmodels.SortType

@Composable
fun SearchSortBar(
    searchSortViewModel: SearchSortViewModel,
    modifier: Modifier = Modifier
) {
    val query by searchSortViewModel.query.collectAsState()
    val sortType by searchSortViewModel.sortType.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = { searchSortViewModel.setQuery(it) },
            placeholder = { Text("Search plants") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SortType.values().forEach { type ->
                FilterChip(
                    selected = type == sortType,
                    onClick = { searchSortViewModel.setSortType(type) },
                    label = { Text(type.name.lowercase().replace("_", " ")) }
                )
            }
        }
    }
}
