package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class MallTypography(
    val heroBanner: TextStyle,
    val sectionHeader: TextStyle,
    val productTitle: TextStyle,
    val priceLarge: TextStyle,
    val priceSmall: TextStyle,
    val body: TextStyle,
    val buttonText: TextStyle
)


val DefaultMallTypography = MallTypography(
    heroBanner = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (-1).sp
    ),
    sectionHeader = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    productTitle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    priceLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2A4BA0)
    ),
    priceSmall = TextStyle(
        fontSize = 12.sp,
        color = Color.Gray
    ),
    body = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    buttonText = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
)


val LocalMallTypography = staticCompositionLocalOf { DefaultMallTypography }