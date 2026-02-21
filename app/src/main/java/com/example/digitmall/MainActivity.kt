package com.example.digitmall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.auth.presentation.login.LoginScreen
import com.example.auth.presentation.registration.RegistrationScreen
import com.example.auth.presentation.welcome.WelcomeScreen
import com.example.digitmall.navigation.AppNavigation
import com.example.digitmall.ui.theme.DigitMallTheme
import com.example.feed.presentation.FeedScreen
import com.example.ui.theme.MallTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MallTheme {
//                AppNavigation()
//                LoginScreen(
//                    onBack = { },
//                    onSuccess = { }
//                )
//                WelcomeScreen(
//                    onLoginClick = {  }
//                ) { }

//                RegistrationScreen(
//                    onBack = {}
//                ) { }

                FeedScreen()
            }
        }
    }
}
