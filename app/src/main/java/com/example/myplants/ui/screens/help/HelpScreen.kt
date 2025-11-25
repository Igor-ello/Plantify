package com.example.myplants.ui.screens.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myplants.R
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel


@Composable
fun HelpScreen(
    modifier: Modifier = Modifier
) {
    SetupTopBar()

    Column(modifier = modifier.padding(16.dp)) {
        Text("Help Screen")
    }
}

@Composable
private fun SetupTopBar() {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val title = stringResource(R.string.screen_help_feedback)

    LaunchedEffect(Unit) {
        topBarState.setTitle(title)
    }
}