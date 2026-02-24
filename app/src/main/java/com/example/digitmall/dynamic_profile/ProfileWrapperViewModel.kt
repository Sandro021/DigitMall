package com.example.digitmall.dynamic_profile

import android.util.Log
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

            Log.d("ProfileWrapper", "Starting profile fetch. User ID is: '$userId'")

            if (userId != null) {

                when (val result = loginRepository.getProfileById(userId)) {
                    is Resource.Success -> {
                        Log.d("ProfileWrapper", "Success! Fetched data: ${result.data}")
                        _currentProfile.value = result.data
                    }

                    is Resource.Error -> {

                        Log.e("ProfileWrapper", "Error: ${result.message}")
                    }

                    else -> {
                        Log.e("ProfileWrapper", "User ID is null! Cannot fetch profile.")
                    }
                }
            }
        }
    }
}