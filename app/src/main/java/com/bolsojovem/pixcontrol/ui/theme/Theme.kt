package com.bolsojovem.pixcontrol.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Google Stitch Theme Engine for Bolso Jovem (PixControl)
 */
private val StitchDarkColorScheme = darkColorScheme(
    primary = StitchPrimaryContainer,
    onPrimary = DarkBackground,
    primaryContainer = StitchPrimary,
    onPrimaryContainer = StitchPrimaryFixed,
    inversePrimary = StitchInversePrimary,
    secondary = StitchSecondaryContainer,
    onSecondary = DarkBackground,
    secondaryContainer = StitchSecondary,
    onSecondaryContainer = StitchSecondaryFixed,
    tertiary = AlertCrimson,
    onTertiary = StitchOnTertiary,
    tertiaryContainer = StitchTertiary,
    onTertiaryContainer = StitchTertiaryContainer,
    background = DarkBackground,
    onBackground = TextPrimaryDark,
    surface = DarkSurfaceCard,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkSurfaceInput,
    onSurfaceVariant = TextSecondaryDark,
    outline = DarkBorderHairline,
    error = AlertCrimson,
    onError = StitchOnTertiary,
    errorContainer = StitchTertiaryContainer,
    onErrorContainer = StitchOnTertiaryContainer
)

private val StitchLightColorScheme = lightColorScheme(
    primary = StitchPrimary,
    onPrimary = StitchOnPrimary,
    primaryContainer = StitchPrimaryContainer,
    onPrimaryContainer = StitchOnPrimaryContainer,
    inversePrimary = StitchInversePrimary,
    secondary = StitchSecondary,
    onSecondary = StitchOnSecondary,
    secondaryContainer = StitchSecondaryContainer,
    onSecondaryContainer = StitchOnSecondaryContainer,
    tertiary = StitchTertiary,
    onTertiary = StitchOnTertiary,
    tertiaryContainer = StitchTertiaryContainer,
    onTertiaryContainer = StitchOnTertiaryContainer,
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurfaceCard,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceContainer,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightBorderHairline,
    error = AlertCrimson,
    onError = StitchOnTertiary,
    errorContainer = StitchTertiaryContainer,
    onErrorContainer = StitchOnTertiaryContainer
)

@Composable
fun BolsoJovemTheme(
    darkTheme: Boolean = true, // Neobank Dark Modern por padrão
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) StitchDarkColorScheme else StitchLightColorScheme
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
