package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.auth.presentation.login.LoginScreen
import com.example.auth.presentation.registration.RegistrationScreen
import com.example.auth.presentation.splash.SplashScreen
import com.example.auth.presentation.welcome.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute
@Serializable
data object WelcomeRoute

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute


fun NavGraphBuilder.authGraph(
    navController: NavController,
    onAuthSuccess: () -> Unit
) {
    composable<SplashRoute> {
        SplashScreen(
            navController = navController,
            firebaseAuth = FirebaseAuth.getInstance()
        )
    }

    composable<WelcomeRoute> {
        WelcomeScreen(
            onLoginClick = { navController.navigate(LoginRoute) },
            onRegisterClick = { navController.navigate(RegisterRoute) },
            onAlreadyLoggedIn = {
                onAuthSuccess()
            }
        )
    }

    composable<LoginRoute> {
        LoginScreen(
            onSuccess = { onAuthSuccess() },
            onBack = { navController.popBackStack() }
        )
    }

    composable<RegisterRoute> {
        RegistrationScreen(
            onSuccess = { onAuthSuccess() },
            onBack = { navController.popBackStack() }
        )
    }
}
