package com.example.digitmall.ui.theme

import androidx.lifecycle.ViewModel
import com.example.data.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppRootViewModel @Inject constructor(
    val networkMonitor: NetworkMonitor
) : ViewModel()