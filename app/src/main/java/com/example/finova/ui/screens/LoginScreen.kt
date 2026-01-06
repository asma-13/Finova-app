
package com.example.finova.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finova.navigation.Screen
import com.example.finova.ui.components.FilledGoldButton
import com.example.finova.ui.components.FinovaLogo
import com.example.finova.ui.components.StyledTextField
import com.example.finova.ui.theme.FinovaGold
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.ui.theme.FinovaTheme
import com.example.finova.viewmodels.LoginUiState
import com.example.finova.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val uiState by loginViewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                loginViewModel.resetUiState()
            }
            is LoginUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                loginViewModel.resetUiState()
            }
            else -> {}
        }
    }

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FinovaLogo(modifier = Modifier.padding(bottom = 24.dp))

            Text("Welcome Back", style = MaterialTheme.typography.headlineMedium, color = FinovaGold)

            Spacer(modifier = Modifier.height(32.dp))

            StyledTextField(
                value = email,
                onValueChange = loginViewModel::onEmailChange,
                label = "Email",
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            StyledTextField(
                value = password,
                onValueChange = loginViewModel::onPasswordChange,
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(color = FinovaGold)
            } else {
                FilledGoldButton(
                    onClick = loginViewModel::onSignInClick,
                    text = "Login",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Using a standard OutlinedButton to ensure visibility
                OutlinedButton(
                    onClick = { navController.navigate(Screen.SignUp.route) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FinovaGold),
                ) {
                    Text("Sign Up")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Using a standard OutlinedButton to ensure visibility
                OutlinedButton(
                    onClick = { /* TODO: Implement Google Sign-In */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FinovaGold),
                ) {
                    Text("Sign in with Google")
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF420B0B)
@Composable
fun LoginScreenPreview() {
    FinovaTheme {
        LoginScreen(rememberNavController())
    }
}
