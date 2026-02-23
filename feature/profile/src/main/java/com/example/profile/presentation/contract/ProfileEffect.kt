package com.example.profile.presentation.contract

sealed class ProfileEffect {
    data class ShowSnackbar(val message: String) : ProfileEffect()
}