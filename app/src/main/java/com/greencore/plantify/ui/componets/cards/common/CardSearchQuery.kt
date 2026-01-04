package com.greencore.plantify.ui.componets.cards.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.greencore.plantify.R
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import com.greencore.plantify.ui.componets.icons.AppIcon
import com.greencore.plantify.ui.componets.icons.AppIcons
import java.net.URLEncoder

@Composable
fun CardSearchQuery(
    plantWithPhotos: PlantWithPhotos,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    IconButton(
        onClick = { performSearch(context, plantWithPhotos) },
        modifier = modifier
    ) {
        AppIcon(icon = AppIcons.SearchInBrowser)
    }
}

// Функция для выполнения поиска
private fun performSearch(context: Context, plantWithPhotos: PlantWithPhotos) {
    val query = "${plantWithPhotos.plant.main.genus} ${plantWithPhotos.plant.main.species}"
    val encodedQuery = URLEncoder.encode(query, "UTF-8")

    // TODO установка браузера поумолчанию в настройках / приоритетный браузер
    val searchUrls = listOf(
        "https://yandex.ru/search/?text=$encodedQuery",
        "https://www.google.com/search?q=$encodedQuery",
        "https://ru.wikipedia.org/w/index.php?search=$encodedQuery"
    )

    val url = searchUrls.first()

    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context,
            context.getString(R.string.toast_browser_not_found), Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context,
            context.getString(R.string.toast_browser_opening_error), Toast.LENGTH_SHORT).show()
    }
}