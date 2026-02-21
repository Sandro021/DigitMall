package com.example.ui.theme



import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class MallColors(
    val brandPrimary: Color,
    val onBrandPrimary: Color, // New: Text color for primary buttons
    val brandSecondary: Color,
    val specialPromo: Color,
    val textPrimary: Color,
    val textSecondary: Color, // New: For subtitles or less important text
    val background: Color,
    val surface: Color,       // New: Specifically for Cards/Sheets
    val success: Color,
    val warning: Color,
    val error: Color
)

@Immutable
data class MallSpacing(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)

val LocalMallColors = staticCompositionLocalOf {
    MallColors(
        brandPrimary = Color.Unspecified,
        onBrandPrimary = Color.Unspecified,
        brandSecondary = Color.Unspecified,
        specialPromo = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified,
        success = Color.Unspecified,
        warning = Color.Unspecified,
        error = Color.Unspecified
    )
}

val LocalMallSpacing = staticCompositionLocalOf { MallSpacing() }