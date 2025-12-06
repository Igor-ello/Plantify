package com.example.myplants.ui.screens.plant

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import kotlin.math.min
import androidx.core.graphics.scale

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