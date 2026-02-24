package com.example.digitmall.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digitmall.navigation.AppNavigation
import com.example.digitmall.ui.theme.AppRootViewModel

@Composable
fun AppRoot(
    vm: AppRootViewModel = hiltViewModel()
) {
    val isOnline by vm.networkMonitor.isOnline.collectAsState()

    Scaffold(
        topBar = {
            if (!isOnline) OfflineBanner()
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            AppNavigation() // your existing NavHost
        }
    }
}

@Composable
fun OfflineBanner() {
    Box(
        Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .background(Color(0xFFB00020))
            .padding(12.dp)
    ) {
        Text(
            text = "No internet connection",
            color = Color.White
        )
    }
}