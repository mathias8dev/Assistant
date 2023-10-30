package com.mathias8dev.assistant.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object Colors {
    val primary = Color(0xFFF5A623)
    val secondary = Color(0xFFFFEB3B)
    val tertiary = Color(0xFFFFC107)
    val error = Color(0xFFEF4E3A)
    val surface = Color(0xFFFFFBFE)
    val background = Color(0xFFF5F5F5)
    val neutral800 = Color(0xFF262626)
    val neutral200 = Color(0xFFE5E5E5)
    val neutral50 = Color(0xFFFAFAFA)
    val neutral600 = Color(0xFF525252)
    val primaryContainer = Color(254, 249, 195)
    val secondaryContainer = Color(254, 252, 232)

    fun onColor(backgroundColor: Color): Color {
        val luminance = backgroundColor.luminance()

        return if (luminance > 0.5) {
            Color.Black
        } else {
            Color.White
        }
    }
}