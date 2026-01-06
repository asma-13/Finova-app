package com.example.finova.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.navigation.Screen
import com.example.finova.ui.components.FinovaLogo
import com.example.finova.ui.theme.FinovaMaroon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FinovaMaroon),
        contentAlignment = Alignment.Center
    ) {
        FinovaLogo(modifier = Modifier.size(150.dp))
    }

    DisposableEffect(navController) {
        val auth = Firebase.auth
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            val destination = if (user != null) {
                Screen.Dashboard.route
            } else {
                Screen.Login.route
            }
            
            navController.navigate(destination) {
                // Pop the splash screen from the back stack to prevent returning to it
                popUpTo(Screen.Splash.route) { inclusive = true }
                // Ensure we don't create multiple copies of the destination
                launchSingleTop = true
            }
        }
        
        // Adding the listener will trigger it once with the current auth state.
        auth.addAuthStateListener(listener)
        
        // When the effect leaves the screen, remove the listener
        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }
}
