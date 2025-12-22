package com.greencore.plantify.ui.screens.plant

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import kotlin.math.min
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO поместить в нужое место

@Composable
fun rememberImageBitmap(imageData: ByteArray): ImageBitmap? {
    return produceState<ImageBitmap?>(initialValue = null, imageData) {
        value = withContext(Dispatchers.IO) {
            BitmapFactory.decodeByteArray(imageData, 0, imageData.size).asImageBitmap()
        }
    }.value
}

fun bitmapToByteArray(bitmap: Bitmap, maxWidth: Int = 300, maxHeight: Int = 300, quality: Int = 70): ByteArray {
    val ratio = min(maxWidth.toFloat() / bitmap.width, maxHeight.toFloat() / bitmap.height)
    val scaledBitmap = bitmap.scale((bitmap.width * ratio).toInt(), (bitmap.height * ratio).toInt())

    val outputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    return outputStream.toByteArray()
}

// Вспомогательная функция для загрузки bitmap из URI
fun ContentResolver.loadBitmap(uri: Uri): Bitmap? {
    return try {
        val input = openInputStream(uri)
        BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        null
    }
}