package com.example.finova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.finova.navigation.FinovaNavHost
import com.example.finova.ui.theme.FinovaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: com.example.finova.viewmodels.MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val userProfile by mainViewModel.userProfile.collectAsState()

            FinovaTheme {
                CompositionLocalProvider(com.example.finova.ui.state.LocalUserProfile provides userProfile) {
                    FinovaNavHost()
                }
            }
        }
    }
}
