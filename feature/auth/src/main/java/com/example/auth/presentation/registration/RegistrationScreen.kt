package com.example.auth.presentation.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.domain.model.AccountType
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.VerticalSpacing
import com.example.ui.theme.common.IconTextField
import com.example.ui.theme.common.RegisterButton
import kotlinx.coroutines.launch
import com.example.auth.R

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()   //// mxolod droebitt

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                RegistrationSideEffect.NavigateBack -> onBack()
                RegistrationSideEffect.NavigateHome -> onSuccess()
                is RegistrationSideEffect.ShowMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Registered Successfully") // mxolod droebit
                    }
                }
            }
        }
    }

    RegistrationScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegistrationScreenContent(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val c = MallTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(cs.background)
    ) {

        // ✅ CONTENT FIRST (so switch can be drawn on top)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(cs.background)
                .padding(top = 150.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing10))

            IconTextField(
                value = state.email,
                onValueChange = { onEvent(RegistrationEvent.EmailChanged(it)) },
                labelText = "Email",
                leadingIconRes = com.example.ui.R.drawable.mail,
                placeholderText = "example@mail.com",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
            if (state.emailError != null) {
                Text(
                    state.emailError,
                    modifier = Modifier.padding(horizontal = Padding.padding20),
                    color = cs.error
                )
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

            IconTextField(
                value = state.password,
                onValueChange = { onEvent(RegistrationEvent.PasswordChanged(it)) },
                labelText = "Password",
                leadingIconRes = com.example.ui.R.drawable.lock,
                placeholderText = "Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.passwordError != null) {
                Text(
                    state.passwordError,
                    modifier = Modifier.padding(horizontal = Padding.padding20),
                    color = cs.error
                )
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

            IconTextField(
                value = state.repeatPassword,
                onValueChange = { onEvent(RegistrationEvent.RepeatPasswordChanged(it)) },
                labelText = "Repeat Password",
                leadingIconRes = com.example.ui.R.drawable.lock,
                placeholderText = "Repeat Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.repeatPasswordError != null) {
                Text(
                    state.repeatPasswordError,
                    modifier = Modifier.padding(horizontal = Padding.padding20),
                    color = cs.error
                )
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

            if (state.accountType == AccountType.USER) {
                IconTextField(
                    value = state.firstName,
                    onValueChange = { onEvent(RegistrationEvent.FirstNameChanged(it)) },
                    labelText = "First name",
                    leadingIconRes = com.example.ui.R.drawable.user,
                    placeholderText = "First name",
                    imeAction = ImeAction.Next
                )
                if (state.firstNameError != null) {
                    Text(
                        state.firstNameError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }

                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

                IconTextField(
                    value = state.lastName,
                    onValueChange = { onEvent(RegistrationEvent.LastNameChanged(it)) },
                    labelText = "Last name",
                    leadingIconRes = com.example.ui.R.drawable.user,
                    placeholderText = "Last name",
                    imeAction = ImeAction.Next
                )
                if (state.lastNameError != null) {
                    Text(
                        state.lastNameError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }

                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

                IconTextField(
                    value = state.phone,
                    onValueChange = { input ->
                        val filtered = input.filter { it.isDigit() } // only numbers
                        onEvent(RegistrationEvent.PhoneChanged(filtered))
                    },
                    labelText = "Phone",
                    leadingIconRes = com.example.ui.R.drawable.phone,
                    placeholderText = "+995...",
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                )
                if (state.phoneError != null) {
                    Text(
                        state.phoneError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }
            }

            if (state.accountType == AccountType.BUSINESS) {
                IconTextField(
                    value = state.companyName,
                    onValueChange = { onEvent(RegistrationEvent.CompanyNameChanged(it)) },
                    labelText = "Company name",
                    leadingIconRes = com.example.ui.R.drawable.shop,
                    placeholderText = "DigitMall Store",
                    imeAction = ImeAction.Next
                )
                if (state.companyNameError != null) {
                    Text(
                        state.companyNameError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }

                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

                IconTextField(
                    value = state.phone,
                    onValueChange = { input ->
                        val filtered = input.filter { it.isDigit() } // only numbers
                        onEvent(RegistrationEvent.PhoneChanged(filtered))
                    },
                    labelText = "Phone",
                    leadingIconRes = com.example.ui.R.drawable.phone,
                    placeholderText = "+995...",
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                )
                if (state.phoneError != null) {
                    Text(
                        state.phoneError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }

                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

                IconTextField(
                    value = state.location,
                    onValueChange = { onEvent(RegistrationEvent.LocationChanged(it)) },
                    labelText = "Location",
                    leadingIconRes = com.example.ui.R.drawable.location,
                    placeholderText = "Tbilisi",
                    imeAction = ImeAction.Next
                )
                if (state.locationError != null) {
                    Text(
                        state.locationError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }

                Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing20))

                IconTextField(
                    value = state.description,
                    onValueChange = { onEvent(RegistrationEvent.DescriptionChanged(it)) },
                    labelText = "Description",
                    leadingIconRes = com.example.ui.R.drawable.info,
                    placeholderText = "What do you sell?",
                    imeAction = ImeAction.Done
                )
                if (state.descriptionError != null) {
                    Text(
                        state.descriptionError,
                        modifier = Modifier.padding(horizontal = Padding.padding20),
                        color = cs.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(VerticalSpacing.verticalSpacing30))

            RegisterButton(
                text = "Register",
                onClick = { onEvent(RegistrationEvent.RegisterClicked) }
            )
        }

        // ✅ Back icon on top
        Icon(
            painter = painterResource(com.example.ui.R.drawable.back),
            contentDescription = "Back button",
            tint = cs.onBackground,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(Padding.padding20)
                .size(30.dp)
                .clickable { onEvent(RegistrationEvent.BackClicked) }
        )

        // ✅ Switch drawn LAST so it stays visible on top
        AccountTypeSwitch(
            selected = state.accountType,
            onSelect = { onEvent(RegistrationEvent.AccountTypeChanged(it)) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp)
        )
    }
}