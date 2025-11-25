package com.example.myplants.ui.componets.topbar

import androidx.compose.runtime.Composable

data class TopBarUiState(
    val title: String = "MyPlants",
    val showMenu: Boolean = true,
    val showSearch: Boolean = false,
    val searchQuery: String = "",
    val actions: List<TopBarAction> = emptyList(),
)


sealed class TopBarEvent {
    data class SearchQueryChanged(val query: String) : TopBarEvent()
    data class ActionClicked(val actionId: String) : TopBarEvent()
    object BackClicked : TopBarEvent()
}