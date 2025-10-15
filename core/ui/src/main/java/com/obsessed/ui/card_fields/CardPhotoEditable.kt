package com.obsessed.ui.card_fields

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.myplants.models.PlantPhoto


@Composable
fun CardPhotoEditable(
    photo: PlantPhoto?,
    onPhotoSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) { }
            onPhotoSelected(it)
        }
    }

    Box(
        modifier = modifier
            .clickable { launcher.launch("image/*") }
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (photo?.uri != null) {
            AsyncImage(
                model = photo.uri,
                contentDescription = "Plant image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("Add Image", color = Color.DarkGray)
        }
    }
}
