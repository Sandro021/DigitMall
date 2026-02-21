package com.example.ui.theme.common


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.R


@Composable
fun LogoCircle(
    size: Dp = 150.dp,
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int = R.drawable.logo,
    contentDescription: String? = null,
) {
    val actualResId = if (isSystemInDarkTheme()) R.drawable.darklogo else resId

    Image(
        painter = painterResource(actualResId),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}

