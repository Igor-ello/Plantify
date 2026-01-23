package com.greencore.plantify.ui.componets.cards.common

import android.content.ClipData
import android.widget.Toast
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import com.greencore.plantify.R
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import com.greencore.plantify.ui.componets.icons.AppIcon
import com.greencore.plantify.ui.componets.icons.AppIcons
import kotlinx.coroutines.launch

@Composable
fun CardCopyFields(
    plantWithPhotos: PlantWithPhotos,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val clipboard  = LocalClipboard.current

    val scope = rememberCoroutineScope()
    fun copyToClipboard() {
        val textToCopy = plantWithPhotos.plant.main.genus +
                plantWithPhotos.plant.main.species.let { " $it" }

        scope.launch {
            val clipEntry = ClipEntry(
                ClipData.newPlainText("plant", textToCopy)
            )
            clipboard.setClipEntry(clipEntry)
        }

        Toast.makeText(
            context,
            context.getString(R.string.button_copy, textToCopy),
            Toast.LENGTH_SHORT
        ).show()
    }

    IconButton(
        onClick = { copyToClipboard() },
        modifier = modifier
    ) {
        AppIcon(icon = AppIcons.CopyPlant)
    }
}