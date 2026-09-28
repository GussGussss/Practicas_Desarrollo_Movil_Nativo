package com.example.miniproyecto_sistema_de_registro_de_estudiantes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores azules
val BlueFacebook = Color(0xFF1877F2)
val BlueLight = Color(0xFF42A5F5)
val BlueDark = Color(0xFF0D47A1)

private val DarkColorScheme = darkColorScheme(
    primary = BlueFacebook,
    secondary = BlueLight,
    tertiary = BlueDark
)

private val LightColorScheme = lightColorScheme(
    primary = BlueFacebook,
    secondary = BlueLight,
    tertiary = BlueDark,
    background = Color(0xFFE3F2FD),
    surface = Color.White
)

@Composable
fun Miniproyecto_sistema_de_registro_de_estudiantesTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
