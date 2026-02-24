package com.example.auth.presentation.welcome


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.MallTheme
import com.example.auth.R
import com.example.ui.theme.FontSize
import com.example.ui.theme.VerticalSpacing
import com.example.ui.theme.common.LoginButton
import com.example.ui.theme.common.LogoCircle
import com.example.ui.theme.common.RegisterButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onAlreadyLoggedIn: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            onAlreadyLoggedIn()
        }
    }


    WelcomeScreenContent(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick
    )

}

@Composable
private fun WelcomeScreenContent(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val c = MallTheme.colors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        LogoCircle(size = 280.dp)

        Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing40))

        LoginButton(stringResource(R.string.login)) { onLoginClick() }

        Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

        RegisterButton(stringResource(R.string.register)) { onRegisterClick() }
    }
}

@Preview
@Composable
private fun WelcomeScreenContentPreview() {
    // Force a theme for preview (optional)
    MallTheme(darkTheme = false) {
        WelcomeScreenContent(
            onLoginClick = { },
            onRegisterClick = { }
        )
    }
}

@Composable
private fun WelcomeText() {
    val c = MallTheme.colors

    Text(
        text = stringResource(R.string.welcome_to_online_mall),
        fontSize = FontSize.fontSize60,
        color = c.brandPrimary
    )

}