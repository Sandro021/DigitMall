package com.example.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<LoginSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> _state.update { it.copy(email = event.value, emailError = null) }
            is LoginEvent.PasswordChanged -> _state.update { it.copy(password = event.value, passwordError = null) }
            LoginEvent.BackClicked -> viewModelScope.launch { _sideEffect.send(LoginSideEffect.NavigateBack) }
            LoginEvent.LoginClicked -> submit()
        }
    }

    private fun submit() {
        if (_state.value.isLoading) return

        val email = _state.value.email.trim()
        val pass = _state.value.password

        var hasError = false

        if (email.isBlank()) {
            hasError = true
            _state.update { it.copy(emailError = "Email can't be empty") }
        }

        if (pass.isBlank()) {
            hasError = true
            _state.update { it.copy(passwordError = "Password can't be empty") }
        }

        if (hasError) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                loginUseCase(email, pass)

                _state.update { it.copy(isLoading = false) }
                _sideEffect.send(LoginSideEffect.NavigateHome)

            } catch (e: com.google.firebase.auth.FirebaseAuthInvalidCredentialsException) {

                _state.update { it.copy(isLoading = false) }
                _state.update {
                    it.copy(passwordError = "Incorrect email or password")
                }

            } catch (e: com.google.firebase.auth.FirebaseAuthInvalidUserException) {

                _state.update { it.copy(isLoading = false) }
                _state.update {
                    it.copy(passwordError = "Incorrect email or password")
                }

            } catch (e: Exception) {

                _state.update { it.copy(isLoading = false) }
                _sideEffect.send(
                    LoginSideEffect.ShowMessage("Something went wrong")
                )
            }
        }
    }
}