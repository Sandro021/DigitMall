package com.example.auth.presentation.splash

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth.navigation.SplashRoute
import com.example.auth.navigation.WelcomeRoute
import com.example.shop_feed.presentation.navigation.ShopListRoute
import com.example.ui.R
import com.example.ui.theme.common.LogoLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    firebaseAuth: FirebaseAuth
) {

    LaunchedEffect(Unit) {
        delay(800)

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            navController.navigate(ShopListRoute) {
                popUpTo(SplashRoute) { inclusive = true }
            }
        } else {
            navController.navigate(WelcomeRoute) {
                popUpTo(SplashRoute) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LogoLoader(
            size = 90.dp,
            logoRes = R.drawable.logo,
            ringColor = Color.Black
        )
    }
}