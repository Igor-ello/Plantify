package com.example.myplants.ui.componets.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.myplants.core.ui.theme.OnPrimaryWhite
import com.example.myplants.core.ui.theme.PrimaryGreen
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    topBarState: TopBarStateViewModel,
    drawerState: DrawerState? = null,
    scope: CoroutineScope? = null
) {
    val state by topBarState.uiState

    TopAppBar(
        title = {
            if (state.showSearch) {
                TextField(
                    value = state.searchQuery,
                    onValueChange = { query ->
                        topBarState.updateSearchQuery(query)
                        topBarState.sendEvent(TopBarEvent.SearchQueryChanged(query))
                    },
                    placeholder = { Text("Search") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                TitleLarge(state.title, color = OnPrimaryWhite)
            }
        },
        navigationIcon = {
            if (state.showMenu && drawerState != null && scope != null) {
                IconButton(
                    onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }
                ) {
                    Icon(Icons.Default.Menu, contentDescription = null, tint = OnPrimaryWhite)
                }
            }
        },
        actions = {
            state.actions.forEach { action ->
                IconButton(
                    onClick = {
                        topBarState.sendEvent(TopBarEvent.ActionClicked(action.id))
                        action.onClick()
                    }
                ) {
                    action.icon()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryGreen,
            titleContentColor = OnPrimaryWhite,
            navigationIconContentColor = OnPrimaryWhite,
            actionIconContentColor = OnPrimaryWhite
        )
    )
}
