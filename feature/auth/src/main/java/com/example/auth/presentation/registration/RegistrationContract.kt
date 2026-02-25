package com.example.auth.presentation.registration

import com.example.auth.domain.model.AccountType

data class RegistrationState(
    val accountType: AccountType = AccountType.USER,

    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",

    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",

    val companyName: String = "",
    val location: String = "",
    val description: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneError: String? = null,
    val companyNameError: String? = null,
    val locationError: String? = null,
    val descriptionError: String? = null,

    val isLoading: Boolean = false
)

sealed interface RegistrationEvent {
    data class AccountTypeChanged(val type: AccountType) : RegistrationEvent

    data class EmailChanged(val v: String) : RegistrationEvent
    data class PasswordChanged(val v: String) : RegistrationEvent
    data class RepeatPasswordChanged(val v: String) : RegistrationEvent

    data class FirstNameChanged(val v: String) : RegistrationEvent
    data class LastNameChanged(val v: String) : RegistrationEvent
    data class PhoneChanged(val v: String) : RegistrationEvent

    data class CompanyNameChanged(val v: String) : RegistrationEvent
    data class LocationChanged(val v: String) : RegistrationEvent
    data class DescriptionChanged(val v: String) : RegistrationEvent

    object RegisterClicked : RegistrationEvent
    object BackClicked : RegistrationEvent
}

sealed interface RegistrationSideEffect {
    object NavigateBack : RegistrationSideEffect
    object NavigateHome : RegistrationSideEffect
    data class ShowMessage(val message: String) : RegistrationSideEffect
}
