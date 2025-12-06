package com.example.myplants.ui.componets.cards.common

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.ui.screens.plant.loadBitmap


@Composable
fun CardPhotoEditable(
    photo: PlantPhoto? = null,
    onReplace: ((Bitmap) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onAdd: ((Bitmap) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Лаунчер для выбора фото из галереи
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) { }

            val bitmap = context.contentResolver.loadBitmap(uri) ?: return@let

            when {
                photo != null && onReplace != null -> onReplace(bitmap)
                photo == null && onAdd != null -> onAdd(bitmap)
            }
        }
    }

    Box(
        modifier = modifier
            .clickable { launcher.launch("image/*") }
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        when {
            photo != null -> {
                // Фото существующее → показываем либо uri, либо imageData
                if (photo.uri != null) {
                    AsyncImage(
                        model = photo.uri,
                        contentDescription = "Plant image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (photo.imageData != null) {
                    val bitmap = BitmapFactory.decodeByteArray(photo.imageData, 0, photo.imageData.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Plant image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("No image", color = Color.DarkGray)
                }

                // Кнопка удаления
                if (onDelete != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(24.dp)
                            .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                            .clickable { onDelete() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            else -> {
                // Нет фото → кнопка добавления
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add photo",
                    modifier = Modifier.size(36.dp),
                    tint = Color.DarkGray
                )
            }
        }
    }
}
