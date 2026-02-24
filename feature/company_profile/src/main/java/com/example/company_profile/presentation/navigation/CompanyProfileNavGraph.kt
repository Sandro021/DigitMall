package com.example.company_profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.company_profile.presentation.CompanyProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object CompanyProfileRoute

fun NavGraphBuilder.companyProfileNavGraph(
    onBackClick: () -> Unit,
) {
    composable<CompanyProfileRoute> {
        CompanyProfileScreen(onBack = onBackClick)
    }
}