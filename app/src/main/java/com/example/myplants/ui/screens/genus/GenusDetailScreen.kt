package com.example.myplants.ui.screens.genus

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.cards.common.CardDeleteButton
import com.example.myplants.ui.componets.cards.genus.GenusCardFull
import com.example.myplants.ui.componets.topbar.TopBarAction
import com.example.myplants.ui.screens.topbar.TopBarStateViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GenusDetailScreen(
    viewModel: GenusDetailViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val editedGenus by viewModel.editedGenus.observeAsState()
    var isEditing by remember { mutableStateOf(false) }

    if (editedGenus == null) {
        Text("Genus not found", modifier = modifier.padding(16.dp))
        return
    }

    // --- Настройка TopBar ---
    LaunchedEffect(isEditing, editedGenus) {
        val title = if (isEditing) "Edit: ${editedGenus?.main?.genus}"
        else editedGenus?.main?.genus!!.ifBlank { "Genus" }

        topBarState.setTitle(title)

        val actions = if (isEditing) {
            listOf(
                TopBarAction(
                    id = "cancel",
                    icon = { Icon(Icons.Default.Close, contentDescription = "Cancel") },
                    onClick = {
                        viewModel.resetChanges()
                        isEditing = false
                    }
                ),
                TopBarAction(
                    id = "save",
                    icon = { Icon(Icons.Default.Check, contentDescription = "Save") },
                    onClick = {
                        viewModel.saveChanges()
                        isEditing = false
                        topBarState.setTitle(editedGenus!!.main.genus.ifBlank { "Genus" })
                    }
                )
            )
        } else {
            listOf(
                TopBarAction(
                    id = "edit",
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Edit") },
                    onClick = { isEditing = true }
                )
            )
        }

        topBarState.setActions(actions)
    }

    DisposableEffect(Unit) {
        onDispose { topBarState.showMenuButton(false) }
    }

    // --- UI экрана ---
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column {
            GenusCardFull(
                genus = editedGenus!!,
                editable = isEditing,
                onValueChange = { updatedGenus ->
                    viewModel.updateEditedGenus(updatedGenus as Genus)
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppButton(
                        onClick = {
                            viewModel.saveChanges()
                            isEditing = false
                            topBarState.setTitle(editedGenus!!.main.genus.ifBlank { "Genus" })
                        },
                        enabled = editedGenus?.main?.genus?.isNotBlank() == true,
                        text = stringResource(R.string.button_save)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CardDeleteButton(
                onDeleteConfirmed = { viewModel.deleteGenus { navController.popBackStack() } },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}