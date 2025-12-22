package com.greencore.plantify.ui.componets.cards.genus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.PlantEntityInterface
import com.greencore.plantify.core.ui.theme.AppTheme
import com.greencore.plantify.domain.usecase.initialization.DataInitializer
import com.greencore.plantify.ui.componets.cards.common.CardDetailContent

@Composable
fun GenusCardFull(
    genus: Genus,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardDetailContent(
                showSpecies = false,
                entity = genus,
                editable = editable,
                onValueChange = onValueChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenusCardFullPreview() {
    AppTheme {
        val genus = DataInitializer.getBlankGenus()

        Column(modifier = Modifier.padding(16.dp)) {
            GenusCardFull(
                genus = genus,
                editable = true,
                onValueChange = { /* обновление данных */ }
            )
        }
    }
}