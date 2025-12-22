package com.greencore.plantify.ui.componets.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BackupItemRow(
    file: File,
    onRestoreClick: (File) -> Unit,
    onDeleteClick: (File) -> Unit,
    onPreviewClick: (File) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(file.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            val date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(file.lastModified()))
            Text(date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(formatFileSize(file.length()), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = { onRestoreClick(file) }) { Text("Restore") }
            IconButton(onClick = { onDeleteClick(file) }) { Icon(Icons.Default.Delete, contentDescription = "Delete") }
            IconButton(onClick = { onPreviewClick(file) }) { Icon(Icons.Default.PlayArrow, contentDescription = "Preview") }
        }
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
