package com.example.myplants.ui.componets.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.ui.theme.MyPlantsTheme
import com.example.myplants.ui.theme.OnSecondaryDark

@Composable
private fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = OnSecondaryDark,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = style,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun BodyLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = OnSecondaryDark,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun BodyMedium(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun TitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = OnSecondaryDark,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun LabelLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = OnSecondaryDark,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun LabelSmall(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = OnSecondaryDark,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextAllPreview() {
    MyPlantsTheme {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TitleLarge("TitleLarge: Крупный заголовок")
            BodyLarge("BodyLarge: Основной текст приложения")
            BodyMedium("BodyMedium: Второстепенный текст, подписи, пояснения")
            LabelLarge("LabelLarge: Текст на кнопках, ярлыках, метках")
            LabelSmall("LabelSmall: Мелкий текст для подсказок, маленьких подписей")
        }
    }
}
