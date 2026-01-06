
package com.example.finova.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    private fun validateInput(): Boolean {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _uiState.value = LoginUiState.Error("Email and password cannot be empty.")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _uiState.value = LoginUiState.Error("Email is badly formatted.")
            return false
        }
        if (_password.value.length < 6) {
            _uiState.value = LoginUiState.Error("Password must be at least 6 characters long.")
            return false
        }
        return true
    }

    fun onSignInClick() {
        if (!validateInput()) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                authRepository.signIn(_email.value, _password.value)
                _uiState.value = LoginUiState.Success
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onSignUpClick() {
        if (!validateInput()) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                authRepository.createUser(_email.value, _password.value)
                // Automatically sign in the user after a successful sign-up
                authRepository.signIn(_email.value, _password.value)
                _uiState.value = LoginUiState.Success
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onGoogleSignInClick() {
        // TODO: Implement Google Sign-In
    }

    fun resetUiState() {
        _uiState.value = LoginUiState.Idle
    }
}
