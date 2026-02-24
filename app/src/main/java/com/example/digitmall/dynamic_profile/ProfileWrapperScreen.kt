package com.example.digitmall.dynamic_profile


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth.domain.model.AccountType
import com.example.company_profile.presentation.CompanyProfileScreen
import com.example.profile.presentation.ProfileScreen


@Composable
fun ProfileWrapperScreen(
    onBack: () -> Unit,
    viewModel: ProfileWrapperViewModel = hiltViewModel()
) {
    val profile by viewModel.currentProfile.collectAsStateWithLifecycle()

    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        when (profile!!.accountType) {
            AccountType.BUSINESS -> {
                CompanyProfileScreen(
                    onBack = onBack,
                )
            }

            AccountType.USER -> {
                ProfileScreen(onNavigateBack = onBack)
            }
        }
    }
}




