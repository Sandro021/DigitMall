package com.example.digitmall.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.digitmall.ui.theme.AppRoot
import com.example.ui.theme.MallTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MallTheme {
                AppRoot()
//                val viewModel: ItemViewModel = hiltViewModel()
//                val state by viewModel.state.collectAsState()
//
//                LaunchedEffect(Unit) {
//                    viewModel.onEvent(
//                        ItemContract.Event.Load(
//                            shopId = "1",
//                            itemId = "7"
//                        )
//                    )
//                }
//
//                ItemScreen(
//                    state = state,
//                    onEvent = viewModel::onEvent
//                )
            }
        }
    }
}