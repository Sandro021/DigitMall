package com.example.profile.presentation.navigation

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profile.presentation.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
data object ProfileRoute


fun NavGraphBuilder.profileNavGraph(
    onBackClick: () -> Unit,
    ) {
    composable<ProfileRoute> {
        ProfileScreen(
            onNavigateBack = onBackClick
        )
    }
}