
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
import com.example.finova.viewmodels.SignUpUiState
import com.example.finova.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val uiState by signUpViewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        when (val state = uiState) {
            is SignUpUiState.Success -> {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                signUpViewModel.resetUiState()
            }
            is SignUpUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                signUpViewModel.resetUiState()
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

            Text("Create Account", style = MaterialTheme.typography.headlineMedium, color = FinovaGold)

            Spacer(modifier = Modifier.height(32.dp))

            StyledTextField(
                value = email,
                onValueChange = signUpViewModel::onEmailChange,
                label = "Email",
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is SignUpUiState.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            StyledTextField(
                value = password,
                onValueChange = signUpViewModel::onPasswordChange,
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is SignUpUiState.Loading
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState is SignUpUiState.Loading) {
                CircularProgressIndicator(color = FinovaGold)
            } else {
                FilledGoldButton(
                    onClick = signUpViewModel::onSignUpClick,
                    text = "Sign Up",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FinovaGold),
                ) {
                    Text("Back to Login")
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF420B0B)
@Composable
fun SignUpScreenPreview() {
    FinovaTheme {
        SignUpScreen(rememberNavController())
    }
}
