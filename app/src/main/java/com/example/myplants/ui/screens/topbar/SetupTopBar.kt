package com.example.myplants.ui.screens.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource

@Composable
fun SetupTopBar(
    uiStateViewModel: UiStateViewModel,
    titleRes: Int,
    showBack: Boolean = false,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    val title = stringResource(titleRes)
    LaunchedEffect(titleRes, showBack, actions) {
        uiStateViewModel.setDrawerTitle(title)
        uiStateViewModel.showBackButton(showBack)
        uiStateViewModel.setTopBarActions(actions)
    }
}
