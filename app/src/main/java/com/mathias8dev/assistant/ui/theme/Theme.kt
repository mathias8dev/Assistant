package com.mathias8dev.assistant.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Colors.primary,
    secondary = Colors.secondary,
    tertiary = Colors.tertiary,
    background = Color(0xFF312F2F),
    surface = Color(0xFF1B1A1B),
    primaryContainer = Colors.primaryContainer,
    secondaryContainer = Colors.secondaryContainer,
    onPrimaryContainer = Colors.onColor(Colors.primaryContainer),
    onSecondaryContainer = Colors.onColor(Colors.secondaryContainer),
    onPrimary = Colors.onColor(Colors.primary),
    onSecondary = Colors.onColor(Colors.primary),
    onTertiary = Colors.onColor(Colors.primary),
    onBackground = Color.Black,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Colors.primary,
    secondary = Colors.secondary,
    tertiary = Colors.tertiary,
    background = Colors.background,
    surface = Colors.surface,
    primaryContainer = Colors.primaryContainer,
    secondaryContainer = Colors.secondaryContainer,
    onPrimaryContainer = Colors.onColor(Colors.primaryContainer),
    onSecondaryContainer = Colors.onColor(Colors.secondaryContainer),
    onPrimary = Colors.onColor(Colors.primary),
    onSecondary = Colors.onColor(Colors.primary),
    onTertiary = Colors.onColor(Colors.primary),
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun AssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}