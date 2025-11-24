package com.example.myplants.ui.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.myplants.core.ui.theme.OnPrimaryWhite
import com.example.myplants.core.ui.theme.PrimaryGreen
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: TopBarStateViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    TopAppBar(
        title = { TitleLarge(uiState.drawerTitle, color = OnPrimaryWhite) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = OnPrimaryWhite)
            }
        },
        actions = uiState.topBarActions ?: {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryGreen,
            titleContentColor = OnPrimaryWhite,
            navigationIconContentColor = OnPrimaryWhite,
            actionIconContentColor = OnPrimaryWhite
        )
    )
}
