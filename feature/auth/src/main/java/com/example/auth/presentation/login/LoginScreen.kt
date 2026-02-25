package com.example.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.R
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.VerticalSpacing
import com.example.ui.theme.common.IconTextField
import com.example.ui.theme.common.LogoCircle
import com.example.ui.theme.common.RegisterButton


@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                LoginSideEffect.NavigateBack -> onBack()
                LoginSideEffect.NavigateHome -> onSuccess()
                is LoginSideEffect.ShowMessage -> {

                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val c = MallTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(cs.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoCircle()

                Text(
                    text = "Digit Mall",
                    fontSize = 45.sp,
                    color = c.brandPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            IconTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                labelText = stringResource(R.string.email),
                leadingIconRes = com.example.ui.R.drawable.mail,
                placeholderText = "example@mail.com",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            if (state.emailError != null) {
                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing8))
                Text(
                    text = state.emailError,
                    modifier = Modifier.padding(horizontal = Padding.padding20),
                    color = cs.error
                )
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

            IconTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                labelText = stringResource(R.string.password),
                leadingIconRes = com.example.ui.R.drawable.lock,
                placeholderText = "Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = PasswordVisualTransformation()
            )

            if (state.passwordError != null) {
                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing8))
                Text(
                    text = state.passwordError,
                    modifier = Modifier.padding(horizontal = Padding.padding20),
                    color = cs.error
                )
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

            RegisterButton(
                text = stringResource(R.string.login),
                onClick = { onEvent(LoginEvent.LoginClicked) }
            )
        }

        Icon(
            painter = painterResource(com.example.ui.R.drawable.back),
            contentDescription = "Back button",
            tint = cs.onBackground,
            modifier = Modifier.padding(horizontal = Padding.padding20)
                .size(30.dp)
                .clickable { onEvent(LoginEvent.BackClicked) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenContentPreview() {
    MallTheme(darkTheme = false) {
        LoginScreenContent(
            state = LoginState(
                email = "test@mail.com",
                password = "123456"
            ),
            onEvent = {}
        )
    }
}