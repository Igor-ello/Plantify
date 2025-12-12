package com.example.myplants.ui.componets.cards.common

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.ui.screens.plant.loadBitmap
import com.example.myplants.ui.screens.plant.rememberImageBitmap


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

    // Асинхронная загрузка imageData
    val imageBitmap = photo?.imageData?.let { data ->
        rememberImageBitmap(data)
    }

    Box(
        modifier = modifier
            .clickable { launcher.launch("image/*") }
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        when {
            photo != null -> {
                if (photo.uri != null) {
                    // Используем AsyncImage для uri
                    AsyncImage(
                        model = photo.uri,
                        contentDescription = "Plant image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (imageBitmap != null) {
                    // Размытый фон
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Blurred background",
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(25.dp),
                        contentScale = ContentScale.Crop
                    )
                    // Основное фото
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Picture of a plant",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
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