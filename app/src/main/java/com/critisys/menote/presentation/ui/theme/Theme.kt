package com.critisys.menote.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Dark,
    primaryVariant = Color.DarkGray,
    secondary = DarkGray,
    onSecondary = Color.LightGray,
    background = Dark,
    onBackground = Color.LightGray,
    surface = DarkGray,
    onSurface = WhiteSmoke,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = WhiteSmoke,
    primaryVariant = simpleWhite,
    secondary =  simpleWhite,
    background = White,
    onBackground = Dark,
    surface = WhiteSmoke,
    onSurface = Dark,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MeNoteTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}