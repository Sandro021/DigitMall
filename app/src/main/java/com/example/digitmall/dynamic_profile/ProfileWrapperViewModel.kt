package com.example.digitmall.dynamic_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.Profile
import com.example.auth.domain.model.Resource
import com.example.auth.domain.repository.LoginRepository
import com.example.data.CurrentUserProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileWrapperViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _currentProfile = MutableStateFlow<Profile?>(null)
    val currentProfile: StateFlow<Profile?> = _currentProfile.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val userId = currentUserProvider.currentUserId


            if (userId != null) {

                when (val result = loginRepository.getProfileById(userId)) {
                    is Resource.Success -> {
                        _currentProfile.value = result.data
                    }

                    is Resource.Error -> {

                    }

                    else -> {}
                }
            }
        }
    }
}