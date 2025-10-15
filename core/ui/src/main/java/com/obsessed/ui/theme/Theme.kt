package com.obsessed.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


private val LightColorScheme = lightColorScheme(
    primary = Shadow5,
    secondary = Ocean3,
    background = Neutral0,
    surface = FunctionalGrey,
    onPrimary = Neutral0,
    onSecondary = Neutral0,
    onBackground = Neutral7,
    onSurface = Neutral7,
    error = FunctionalRed
)

private val DarkColorScheme = darkColorScheme(
    primary = Shadow1,
    secondary = Ocean2,
    background = Neutral8,
    surface = FunctionalDarkGrey,
    onPrimary = Neutral7,
    onSecondary = Neutral7,
    onBackground = Shadow1,
    onSurface = Shadow1,
    error = FunctionalRedDark
)

@Composable
fun MyPlantsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        //LocalExtraColors provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
