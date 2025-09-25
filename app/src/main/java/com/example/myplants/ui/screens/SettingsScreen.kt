package com.example.myplants.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myplants.plants.PlantsViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsScreen(viewModel: PlantsViewModel) {
    val backups by viewModel.backups.observeAsState(emptyList())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var showCreateConfirm by remember { mutableStateOf(false) }
    var selectedFileForRestore by remember { mutableStateOf<File?>(null) }
    var showRestoreConfirm by remember { mutableStateOf(false) }
    var restoreReplaceMode by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { viewModel.refreshBackups() }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showCreateConfirm = true }) {
            Text("Создать резервную копию")
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
                            selectedFileForRestore = it
                            showRestoreConfirm = true
                        },
                        onOpenClick = { /* можно показать содержимое */ }
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

    if (showRestoreConfirm && selectedFileForRestore != null) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirm = false; selectedFileForRestore = null },
            title = { Text("Восстановление") },
            text = {
                Column {
                    Text("Выберите режим восстановления для ${selectedFileForRestore?.name}:")
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = restoreReplaceMode, onClick = { restoreReplaceMode = true })
                        Text("Заменить текущую базу")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = !restoreReplaceMode, onClick = { restoreReplaceMode = false })
                        Text("Слить с текущими данными")
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Replace удалит все текущие записи и вставит данные из бэкапа. Merge добавит/обновит записи.")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val file = selectedFileForRestore
                    showRestoreConfirm = false
                    selectedFileForRestore = null
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
            dismissButton = { TextButton(onClick = { showRestoreConfirm = false; selectedFileForRestore = null }) { Text("Отмена") } }
        )
    }
}

@Composable
private fun BackupItemRow(
    file: File,
    onRestoreClick: (File) -> Unit,
    onOpenClick: (File) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .clickable { onOpenClick(file) }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(file.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            val date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(file.lastModified()))
            Text(date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(formatFileSize(file.length()), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        TextButton(onClick = { onRestoreClick(file) }) { Text("Restore") }
    }
}

private fun formatFileSize(bytes: Long): String {
    if (bytes <= 0) return "0 B"
    val units = arrayOf("B","KB","MB","GB","TB")
    var b = bytes.toDouble()
    var i = 0
    while (b >= 1024 && i < units.size - 1) { b /= 1024; i++ }
    return "%.1f %s".format(b, units[i])
}
