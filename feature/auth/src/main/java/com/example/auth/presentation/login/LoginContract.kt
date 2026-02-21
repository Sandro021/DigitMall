package com.example.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false
)

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object LoginClicked : LoginEvent
    object BackClicked : LoginEvent
}

sealed interface LoginSideEffect {
    object NavigateBack : LoginSideEffect
    object NavigateHome : LoginSideEffect
    data class ShowMessage(val message: String) : LoginSideEffect
}
