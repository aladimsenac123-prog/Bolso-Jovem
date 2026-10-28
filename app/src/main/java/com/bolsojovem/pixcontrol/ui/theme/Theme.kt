package com.bolsojovem.pixcontrol.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PixMint,
    onPrimary = DarkBackground,
    primaryContainer = PixMintDark,
    onPrimaryContainer = PixMint,
    secondary = FinTechCobalt,
    background = DarkBackground,
    surface = DarkSurfaceCard,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    error = AlertCrimson
)

private val LightColorScheme = lightColorScheme(
    primary = PixMintDark,
    onPrimary = LightBackground,
    primaryContainer = PixMint,
    onPrimaryContainer = TextPrimaryLight,
    secondary = FinTechCobalt,
    background = LightBackground,
    surface = LightSurfaceCard,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    error = AlertCrimson
)

@Composable
fun BolsoJovemTheme(
    darkTheme: Boolean = true, // Padrão Neobank Dark Modern
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
