package com.example.myplants.ui.screens.settings

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myplants.R
import com.example.myplants.ui.componets.base.AppButton
import com.example.myplants.ui.componets.settings.BackupItemRow
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = hiltViewModel()
    SettingsView(viewModel)
}

@Composable
fun SettingsView(
    viewModel: SettingsViewModel
) {
    SetupTopBar()

    val backups by viewModel.backups.collectAsState(emptyList())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showCreateConfirm by remember { mutableStateOf(false) }
    var showRestoreConfirm by remember { mutableStateOf(false) }
    var showJsonPreview by remember { mutableStateOf(false) }
    var restoreReplaceMode by remember { mutableStateOf(true) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    // Лаунчер выбора файла из внутреннего хранилища
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val importedFile = File(context.filesDir, "imported_backup.json")
            inputStream?.use { input ->
                importedFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            scope.launch {
                viewModel.restore(importedFile)
                Toast.makeText(
                    context,
                    context.getString(R.string.toast_file_imported),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(Unit) { viewModel.refreshBackups() }

    Column(modifier = Modifier.padding(16.dp)) {
        AppButton(
            onClick = { showCreateConfirm = true },
            text = stringResource(R.string.action_create_backup)
        )

        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            onClick = { importLauncher.launch("application/json") },
            text = stringResource(R.string.action_import_file)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(stringResource(R.string.text_backups), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (backups.isEmpty()) {
            Text(stringResource(R.string.text_no_backups), color = Color.Gray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(backups) { file ->
                    BackupItemRow(
                        file = file,
                        onRestoreClick = {
                            selectedFile = it
                            showRestoreConfirm = true
                        },
                        onDeleteClick = { f ->
                            scope.launch {
                                viewModel.deleteBackup(f)
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.toast_file_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onPreviewClick = { f ->
                            selectedFile = f
                            showJsonPreview = true
                        }
                    )
                }
            }
        }
    }

    if (showCreateConfirm) {
        AlertDialog(
            onDismissRequest = { showCreateConfirm = false },
            title = { Text(stringResource(R.string.text_create_backup)) },
            text = { Text(stringResource(R.string.text_backup_created)) },
            confirmButton = {
                TextButton(onClick = {
                    showCreateConfirm = false
                    scope.launch {
                        viewModel.backup()
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_backup_created),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) { Text(stringResource(R.string.text_create)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCreateConfirm = false
                }) { Text(stringResource(R.string.text_cancel)) }
            }
        )
    }

    if (showRestoreConfirm && selectedFile != null) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirm = false; selectedFile = null },
            title = { Text(stringResource(R.string.text_recover)) },
            text = {
                Column {
                    Text(
                        stringResource(R.string.text_recovery_mode,
                            selectedFile?.name ?: "")
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = restoreReplaceMode,
                            onClick = { restoreReplaceMode = true })
                        Text(stringResource(R.string.text_replace_db))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = !restoreReplaceMode,
                            onClick = { restoreReplaceMode = false })
                        Text(stringResource(R.string.text_merge_data))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val file = selectedFile
                    showRestoreConfirm = false
                    selectedFile = null
                    if (file != null) {
                        scope.launch {
                            if (restoreReplaceMode) viewModel.restore(file)
                            else viewModel.restoreMerge(file)
                            viewModel.refreshBackups()
                            Toast.makeText(
                                context,
                                context.getString(R.string.toast_recovery_completed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }) { Text(stringResource(R.string.text_recover)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRestoreConfirm = false; selectedFile = null
                }) { Text(stringResource(R.string.button_cancel)) }
            }
        )
    }

    if (showJsonPreview && selectedFile != null) {
        AlertDialog(
            onDismissRequest = { showJsonPreview = false; selectedFile = null },
            title = { Text(stringResource(R.string.text_show_json)) },
            text = {
                val jsonText = remember(selectedFile) {
                    if (selectedFile!!.exists()) selectedFile!!.readText()
                    else context.getString(R.string.text_file_is_empty)
                }
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(jsonText, style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val file = selectedFile ?: return@TextButton
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "application/json"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(intent,
                        context.getString(R.string.text_export_json)))
                    showJsonPreview = false
                    selectedFile = null
                }) { Text(stringResource(R.string.text_export)) }
            },
            dismissButton = {
                TextButton(onClick = { showJsonPreview = false; selectedFile = null })
                { Text(stringResource(R.string.button_close)) }
            }
        )
    }
}

@Composable
private fun SetupTopBar() {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val title = stringResource(R.string.screen_settings)

    LaunchedEffect(Unit) {
        topBarState.setTitle(title)
    }
}
