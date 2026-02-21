package com.example.ui.theme.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun RegisterButton(
    text: String,
    onClick: () -> Unit
) {
    val c = MallTheme.colors

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.height45)
            .padding(horizontal = Padding.padding50),
        shape = Radius.radius16,
        colors = ButtonDefaults.buttonColors(
            containerColor = c.brandPrimary,
            contentColor = c.onBrandPrimary
        )
    ) {
        Text(
            text = text,
            fontSize = FontSize.fontSize16,
            fontWeight = FontWeight.SemiBold,
            color = c.onBrandPrimary
        )
    }
}

@Preview
@Composable
fun RegisterButtonPreview() {
    MallTheme(darkTheme = false) {
        RegisterButton("Register") { }
    }
}