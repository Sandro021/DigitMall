package com.example.ui.theme.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.FontSize
import com.example.ui.theme.Height
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius
import com.example.ui.theme.Stroke

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit
) {
    val c = MallTheme.colors

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.height45)
            .padding(horizontal = Padding.padding50),
        shape = Radius.radius16,
        border = BorderStroke(Stroke.stroke2, c.brandPrimary)
    ) {
        Text(
            text = text,
            fontSize = FontSize.fontSize16,
            fontWeight = FontWeight.SemiBold,
            color = c.brandPrimary
        )
    }
}

@Preview
@Composable
fun LoginButtonPreview() {
    MallTheme(darkTheme = false) {
        LoginButton("login") { }
    }
}