package com.example.profile.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CurrentUserProvider
import com.example.profile.domain.usecase.GetUserProfileUseCase
import com.example.profile.domain.usecase.UpdateUserProfileUseCase
import com.example.profile.presentation.contract.ProfileEffect
import com.example.profile.presentation.contract.ProfileIntent
import com.example.profile.presentation.contract.ProfileState
import com.example.profile.presentation.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ProfileEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleIntent(ProfileIntent.LoadProfile)
    }

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.UpdateProfile -> updateProfile(intent)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val userId = currentUserProvider.currentUserId

            if (userId == null) {
                _state.update { it.copy(error = "User not logged in") }
                return@launch
            }

            getUserProfileUseCase(userId)
                .onSuccess { domainProfile ->
                    _state.update {
                        it.copy(isLoading = false, profile = domainProfile.toUiModel())
                    }
                }
                .onFailure { error ->
                    println("PROFILE_DEBUG: Network call failed because -> ${error.message}")
                    _state.update { it.copy(isLoading = false, error = error.message) }
                    _effect.send(ProfileEffect.ShowSnackbar("Failed to load profile."))
                }
        }
    }

    private fun updateProfile(intent: ProfileIntent.UpdateProfile) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val userId = currentUserProvider.currentUserId

            if (userId == null) {
                _state.update { it.copy(error = "User not logged in") }
                return@launch
            }

            val currentDomainProfile = getUserProfileUseCase(userId).getOrNull()

            if (currentDomainProfile == null) {
                _state.update { it.copy(isLoading = false, error = "Profile not found") }
                return@launch
            }

            updateUserProfileUseCase(
                currentProfile = currentDomainProfile,
                newName = intent.firstName,
                newLastName = intent.lastName,
                newUsername = intent.username,
                newAvatarUriString = intent.newAvatarUriString
            ).onSuccess { updatedDomainProfile ->
                _state.update {
                    it.copy(isLoading = false, profile = updatedDomainProfile.toUiModel())
                }

                val message = if (intent.newAvatarUriString != null) {
                    "Avatar and profile updated successfully!"
                } else {
                    "Profile updated successfully!"
                }
                _effect.send(ProfileEffect.ShowSnackbar(message))

            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
                _effect.send(ProfileEffect.ShowSnackbar(error.message ?: "Update failed"))
            }
        }
    }
}