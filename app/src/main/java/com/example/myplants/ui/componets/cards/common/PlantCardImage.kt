package com.example.myplants.ui.componets.cards.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.ui.componets.cards.plants.PlantCardDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlantCardImage(imageData: ByteArray?) {

    val imageBitmap = if (imageData != null) {
        rememberImageBitmap(imageData)
    } else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(PlantCardDefaults.imageHeight)
            .clip(PlantCardDefaults.cardShape)
    ) {
        if (imageBitmap != null) {

            Image(
                bitmap = imageBitmap,
                contentDescription = "Picture of a plant",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(25.dp),
                contentScale = ContentScale.Crop
            )

            Image(
                bitmap = imageBitmap,
                contentDescription = "Blurred background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

        } else {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "placeholder",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun rememberImageBitmap(imageData: ByteArray): ImageBitmap? {
    return produceState<ImageBitmap?>(initialValue = null, imageData) {
        value = imageData.let { data ->
            withContext(Dispatchers.IO) {
                BitmapFactory.decodeByteArray(data, 0, data.size).asImageBitmap()
            }
        }
    }.value
}
