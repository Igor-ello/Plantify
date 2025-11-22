package com.example.myplants.ui.screens.plant.state.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.core.ui.theme.MyPlantsTheme
import com.example.myplants.ui.screens.plant.state.PlantStateType

@Composable
fun PlantListEmptyState(
    listType: PlantStateType,
    modifier: Modifier = Modifier
) {
    when (listType) {
        PlantStateType.FAVORITES -> FavoritesEmptyState(modifier)
        PlantStateType.WISHLIST -> WishlistEmptyState(modifier)
    }
}

@Composable
fun FavoritesEmptyState(
    modifier: Modifier = Modifier
) {
    GenericEmptyState(
        icon = Icons.Default.Favorite,
        title = "empty_favorites_title",
        description = "empty_favorites_description",
        modifier = modifier
    )
}

@Composable
fun WishlistEmptyState(
    modifier: Modifier = Modifier
) {
    GenericEmptyState(
        icon = Icons.Default.ShoppingCart,
        title = "empty_wishlist_title",
        description = "empty_wishlist_description",
        modifier = modifier
    )
}

@Composable
private fun GenericEmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesEmptyStatePreview() {
    MyPlantsTheme {
        FavoritesEmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun WishlistEmptyStatePreview() {
    MyPlantsTheme {
        WishlistEmptyState()
    }
}