package com.example.myplants.ui.componets.cards.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.ui.screens.plant.rememberImageBitmap

@Composable
fun CardPhoto(imageData: ByteArray?) {
    // Асинхронная загрузка imageData
    val imageBitmap = imageData?.let { data ->
        rememberImageBitmap(data)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (imageBitmap != null) {

            Image(
                bitmap = imageBitmap,
                contentDescription = "Blurred background",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(25.dp),
                contentScale = ContentScale.Crop
            )

            Image(
                bitmap = imageBitmap,
                contentDescription = "Picture of a plant",
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
