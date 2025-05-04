package com.target.targetcasestudy.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RedPrimary = Color(0xFFCC0000)
val DarkText = Color(0xFF333333)
val GrayText = Color(0xFF666666)
val TargetRed = Color(0xFFCC0000)
val DarkGray = Color(0xFF333333)
val LightGray = Color(0xFFAAAAAA)
val Background = Color(0xFFF5F5F5)

private val LightColorPalette = lightColors(
    primary = TargetRed,
    primaryVariant = TargetRed,
    secondary = DarkGray,
    background = Background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkGray,
    onSurface = DarkGray
)

private val DarkColorPalette = darkColors(
    primary = TargetRed,
    primaryVariant = TargetRed,
    secondary = LightGray,
    background = DarkGray,
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun DealTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}