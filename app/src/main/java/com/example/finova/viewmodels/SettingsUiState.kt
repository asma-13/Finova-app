package com.example.finova.viewmodels

import com.example.finova.data.model.UserSettings

sealed class SettingsUiState {
    object Loading : SettingsUiState()
    data class Success(val settings: UserSettings?) : SettingsUiState()
    data class Error(val message: String) : SettingsUiState()
}
