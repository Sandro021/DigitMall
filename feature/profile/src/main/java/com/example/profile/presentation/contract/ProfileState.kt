package com.example.profile.presentation.contract

import com.example.profile.presentation.model.ProfileUiModel

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileUiModel? = null,
    val error: String? = null
)
