package com.example.myplants.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class UiStateViewModel : ViewModel() {
    private var _drawerTitle by mutableStateOf("My Plants")
    val drawerTitle: String get() = _drawerTitle

    // actions с RowScope, чтобы их спокойно вставлять в TopAppBar.actions
    private var _topBarActions by mutableStateOf<(@Composable (RowScope.() -> Unit))?>(null)
    val topBarActions: (@Composable (RowScope.() -> Unit))? get() = _topBarActions

    private var _showBackButton by mutableStateOf(false)
    val showBackButton: Boolean get() = _showBackButton

    fun setDrawerTitle(title: String) { _drawerTitle = title }
    fun resetDrawerTitle() { _drawerTitle = "My Plants" }

    fun setTopBarActions(actions: (@Composable (RowScope.() -> Unit))?) {
        _topBarActions = actions
    }
    fun clearTopBarActions() { _topBarActions = null }

    fun showBackButton(show: Boolean) { _showBackButton = show }
}

