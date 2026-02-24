package com.example.digitmall.dynamic_profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.digitmall.dynamic_profile.ProfileWrapperScreen
import kotlinx.serialization.Serializable

@Serializable
data object DynamicProfileRoute

fun NavGraphBuilder.dynamicProfileNavGraph(
    onBackClick: () -> Unit,
    ) {

    composable<DynamicProfileRoute> {

        ProfileWrapperScreen(
            onBack = onBackClick
        )
    }
}