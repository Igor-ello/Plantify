package com.example.myplants.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.myplants.ui.componets.settings.BackupItemRow
import com.example.myplants.ui.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {

    val backups by viewModel.backups.observeAsState(emptyList())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showCreateConfirm by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var showRestoreConfirm by remember { mutableStateOf(false) }
    var restoreReplaceMode by remember { mutableStateOf(true) }
    var showJsonPreview by remember { mutableStateOf(false) }

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
                Toast.makeText(context, "Файл импортирован", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) { viewModel.refreshBackups() }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showCreateConfirm = true }) {
            Text("Создать резервную копию")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { importLauncher.launch("application/json") }) {
            Text("Импортировать файл")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Доступные резервные копии:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (backups.isEmpty()) {
            Text("Резервные копии отсутствуют", color = Color.Gray)
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
                                Toast.makeText(context, "Файл удалён", Toast.LENGTH_SHORT).show()
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
            title = { Text("Создать бэкап?") },
            text = { Text("Будет создан файл бэкапа в каталоге приложения. Продолжить?") },
            confirmButton = {
                TextButton(onClick = {
                    showCreateConfirm = false
                    scope.launch {
                        viewModel.backup()
                        Toast.makeText(context, "Бэкап создан", Toast.LENGTH_SHORT).show()
                    }
                }) { Text("Создать") }
            },
            dismissButton = { TextButton(onClick = { showCreateConfirm = false }) { Text("Отмена") } }
        )
    }

    if (showRestoreConfirm && selectedFile != null) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirm = false; selectedFile = null },
            title = { Text("Восстановление") },
            text = {
                Column {
                    Text("Выберите режим восстановления для ${selectedFile?.name}:")
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = restoreReplaceMode, onClick = { restoreReplaceMode = true })
                        Text("Заменить текущую базу")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = !restoreReplaceMode, onClick = { restoreReplaceMode = false })
                        Text("Слить с текущими данными")
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
                            Toast.makeText(context, "Восстановление выполнено", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) { Text("Восстановить") }
            },
            dismissButton = { TextButton(onClick = { showRestoreConfirm = false; selectedFile = null }) { Text("Отмена") } }
        )
    }

    if (showJsonPreview && selectedFile != null) {
        AlertDialog(
            onDismissRequest = { showJsonPreview = false; selectedFile = null },
            title = { Text("Просмотр JSON") },
            text = {
                val jsonText = remember(selectedFile) {
                    if (selectedFile!!.exists()) selectedFile!!.readText() else "Файл пуст"
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
                    context.startActivity(Intent.createChooser(intent, "Экспортировать JSON"))
                    showJsonPreview = false
                    selectedFile = null
                }) { Text("Экспорт") }
            },
            dismissButton = { TextButton(onClick = { showJsonPreview = false; selectedFile = null }) { Text("Закрыть") } }
        )
    }
}
