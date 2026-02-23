package com.example.profile.presentation.navigation

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
        ProfileScreen(onNavigateBack = onBackClick)
    }
}