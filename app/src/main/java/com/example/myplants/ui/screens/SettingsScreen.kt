package com.example.myplants.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myplants.plants.PlantsViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsScreen(viewModel: PlantsViewModel) {
    val scope = rememberCoroutineScope()
    var backups by remember { mutableStateOf<List<File>>(emptyList()) }
    var message by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        backups = viewModel.listBackups()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            scope.launch {
                viewModel.backup()
                backups = viewModel.listBackups()
                message = "Резервная копия создана"
            }
        }) {
            Text("Сохранить резервную копию")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(backups) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                viewModel.restore(file)
                                message = "Восстановлено из ${file.name}"
                            }
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(file.name)
                    Text(
                        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                            .format(Date(file.lastModified())),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = Color.Green)
        }
    }
}
