package com.example.ui.theme




import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color


private val LightMallColors = MallColors(
    brandPrimary = Color(0xFF243654),
    onBrandPrimary = Color(0xFFFFFFFF),
    brandSecondary = Color(0xFFFF6E40),
    specialPromo = Color(0xFFF9B023),
    textPrimary = Color(0xFF1B1C1E),
    textSecondary = Color(0xFF6B7280),
    background = Color(0xFFF8F9FB),
    surface = Color(0xFFFFFFFF),
    success = Color(0xFF00C853),
    warning = Color(0xFFFFAB00),
    error = Color(0xFFD32F2F)
)

private val DarkMallColors = MallColors(
    brandPrimary = Color(0xFFF8F9FB),
    onBrandPrimary = Color(0xFF000000),
    brandSecondary = Color(0xFFFF8A65),
    specialPromo = Color(0xFFFFD54F),
    textPrimary = Color(0xFFEEEEEE),
    textSecondary = Color(0xFFB0B3B8),
    background = Color(0xFF243654),
    surface = Color(0xFF243654),
    success = Color(0xFF69F0AE),
    warning = Color(0xFFFFD180),
    error = Color(0xFFEF5350)
)

@Composable
fun MallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkMallColors else LightMallColors
    val spacing = MallSpacing()
    val typography = DefaultMallTypography

    CompositionLocalProvider(
        LocalMallColors provides colors,
        LocalMallSpacing provides spacing,
        LocalMallTypography provides typography
    ) {

        MaterialTheme(
            colorScheme = if (darkTheme) {
                darkColorScheme(
                    primary = colors.brandPrimary,
                    onPrimary = colors.onBrandPrimary,
                    secondary = colors.brandSecondary,
                    background = colors.background,
                    onBackground = colors.textPrimary,
                    surface = colors.surface,
                    onSurface = colors.textPrimary, 
                    error = colors.error
                )
            } else {
                lightColorScheme(
                    primary = colors.brandPrimary,
                    onPrimary = colors.onBrandPrimary,
                    secondary = colors.brandSecondary,
                    background = colors.background,
                    onBackground = colors.textPrimary,
                    surface = colors.surface,
                    onSurface = colors.textPrimary,
                    error = colors.error
                )
            },
            content = content
        )
    }
}

object MallTheme {
    val colors: MallColors
        @Composable
        get() = LocalMallColors.current

    val spacing: MallSpacing
        @Composable
        get() = LocalMallSpacing.current

    val typography: MallTypography
        @Composable
        get() = LocalMallTypography.current
}