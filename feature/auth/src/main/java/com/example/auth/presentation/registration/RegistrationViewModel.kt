package com.example.auth.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.AccountType
import com.example.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<RegistrationSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.AccountTypeChanged -> _state.update { it.copy(accountType = event.type) }

            is RegistrationEvent.EmailChanged -> _state.update { it.copy(email = event.v, emailError = null) }
            is RegistrationEvent.PasswordChanged -> _state.update { it.copy(password = event.v, passwordError = null) }
            is RegistrationEvent.RepeatPasswordChanged -> _state.update { it.copy(repeatPassword = event.v, repeatPasswordError = null) }

            is RegistrationEvent.FirstNameChanged -> _state.update { it.copy(firstName = event.v, firstNameError = null) }
            is RegistrationEvent.LastNameChanged -> _state.update { it.copy(lastName = event.v, lastNameError = null) }
            is RegistrationEvent.PhoneChanged -> _state.update { it.copy(phone = event.v, phoneError = null) }

            is RegistrationEvent.CompanyNameChanged -> _state.update { it.copy(companyName = event.v, companyNameError = null) }
            is RegistrationEvent.LocationChanged -> _state.update { it.copy(location = event.v, locationError = null) }
            is RegistrationEvent.DescriptionChanged -> _state.update { it.copy(description = event.v, descriptionError = null) }

            RegistrationEvent.BackClicked -> viewModelScope.launch {
                _sideEffect.send(RegistrationSideEffect.NavigateBack)
            }

            RegistrationEvent.RegisterClicked -> submit()
        }
    }

    private fun submit() {
        val s = _state.value
        val email = s.email.trim()
        val password = s.password
        val repeat = s.repeatPassword

        fun setError(update: (RegistrationState) -> RegistrationState) {
            _state.update(update)
        }

        var hasError = false

        if (email.isBlank()) { hasError = true; setError { it.copy(emailError = "Email can't be empty") } }
        if (password.isBlank()) { hasError = true; setError { it.copy(passwordError = "Password can't be empty") } }
        if (repeat.isBlank()) { hasError = true; setError { it.copy(repeatPasswordError = "Repeat password") } }
        if (password.isNotBlank() && repeat.isNotBlank() && password != repeat) {
            hasError = true; setError { it.copy(repeatPasswordError = "Passwords do not match") }
        }

        when (s.accountType) {
            AccountType.USER -> {
                if (s.firstName.trim().isBlank()) { hasError = true; setError { it.copy(firstNameError = "First name required") } }
                if (s.lastName.trim().isBlank()) { hasError = true; setError { it.copy(lastNameError = "Last name required") } }
                if (s.phone.trim().isBlank()) { hasError = true; setError { it.copy(phoneError = "Phone required") } }
            }
            AccountType.BUSINESS -> {
                if (s.companyName.trim().isBlank()) { hasError = true; setError { it.copy(companyNameError = "Company name required") } }
                if (s.phone.trim().isBlank()) { hasError = true; setError { it.copy(phoneError = "Phone required") } }
                if (s.location.trim().isBlank()) { hasError = true; setError { it.copy(locationError = "Location required") } }
                if (s.description.trim().isBlank()) { hasError = true; setError { it.copy(descriptionError = "Description required") } }
            }
        }

        if (hasError) return

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                registerUseCase(
                    accountType = s.accountType,
                    email = email,
                    password = password,
                    userFirstName = if (s.accountType == AccountType.USER) s.firstName.trim() else null,
                    userLastName = if (s.accountType == AccountType.USER) s.lastName.trim() else null,
                    phone = s.phone.trim(),
                    companyName = if (s.accountType == AccountType.BUSINESS) s.companyName.trim() else null,
                    location = if (s.accountType == AccountType.BUSINESS) s.location.trim() else null,
                    description = if (s.accountType == AccountType.BUSINESS) s.description.trim() else null
                )

                _state.update { it.copy(isLoading = false) }
                _sideEffect.send(RegistrationSideEffect.NavigateHome)

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _sideEffect.send(RegistrationSideEffect.ShowMessage(e.message ?: "Register failed"))
            }
        }
    }
}