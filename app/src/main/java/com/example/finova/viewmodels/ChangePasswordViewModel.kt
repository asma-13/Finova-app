package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChangePasswordUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class ChangePasswordViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState = _uiState.asStateFlow()

    var currentPassword = MutableStateFlow("")
        private set
    var newPassword = MutableStateFlow("")
        private set
    var confirmPassword = MutableStateFlow("")
        private set

    fun onCurrentPasswordChange(value: String) { currentPassword.value = value }
    fun onNewPasswordChange(value: String) { newPassword.value = value }
    fun onConfirmPasswordChange(value: String) { confirmPassword.value = value }

    fun changePassword() {
        if (newPassword.value != confirmPassword.value) {
            _uiState.value = _uiState.value.copy(errorMessage = "New passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                authRepository.reauthenticateAndChangePassword(currentPassword.value, newPassword.value)
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Failed to change password")
            }
        }
    }

    fun resetState() {
        _uiState.value = ChangePasswordUiState()
        currentPassword.value = ""
        newPassword.value = ""
        confirmPassword.value = ""
    }
}
