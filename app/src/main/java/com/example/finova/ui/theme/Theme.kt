package com.example.finova.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FinovaDarkColorScheme = darkColorScheme(
    primary = FinovaGold,
    onPrimary = FinovaBlack,
    background = FinovaMaroon,
    onBackground = FinovaCream,
    surface = FinovaBlack, // Used for Cards
    onSurface = FinovaCream,
    secondary = FinovaGold,
    onSecondary = FinovaBlack,
    tertiary = FinovaCream,
    onTertiary = FinovaBlack,
    error = Color(0xFFCF6679), // A standard Material red for errors
    onError = Color.Black
)

@Composable
fun FinovaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FinovaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
