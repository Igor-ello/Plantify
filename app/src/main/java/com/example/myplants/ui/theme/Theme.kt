package com.example.myplants.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryWhite,
    secondary = SecondaryMint,
    onSecondary = OnSecondaryDark,
    background = BackgroundLight,
    onBackground = TextBlack,
    surface = SurfaceLight,
    onSurface = TextBlack,
    error = ErrorRed,
    onError = OnErrorWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryWhite,
    secondary = SecondaryMint,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    error = ErrorRed,
    onError = OnErrorWhite
)

object CardColors {
    val card1 = CardColor1
    val card2 = CardColor2
    val card3 = CardColor3
    val card4 = CardColor4
    val card5 = CardColor5
    val card6 = CardColor6
    val card7 = CardColor7
}

object StateColors {
    val success = SuccessGreen
    val warning = WarningYellow
    val error = ErrorRed
}

object ButtonColors {
    val primary = GreenPrimary
    val secondary = RedPrimary
}


@Composable
fun MyPlantsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}