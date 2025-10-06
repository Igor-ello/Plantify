package com.example.myplants.ui.componets.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.models.Genus
import com.example.myplants.models.PlantEntityInterface
import com.example.myplants.ui.componets.card_fields.CardBasicContent
import com.example.myplants.ui.componets.card_fields.CardDetailContent
import com.example.myplants.ui.theme.GreenLight

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
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        CardBasicContent(genus, editable, onValueChange)
        CardDetailContent(genus, editable, onValueChange)
    }
}