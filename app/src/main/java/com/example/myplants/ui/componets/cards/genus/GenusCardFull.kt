package com.example.myplants.ui.componets.cards.genus

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
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.PlantEntityInterface
import com.example.myplants.core.ui.theme.MyPlantsTheme
import com.example.myplants.domain.usecase.initialization.DataInitializer
import com.example.myplants.ui.componets.cards.common.CardDetailContent

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
    MyPlantsTheme {
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