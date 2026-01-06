package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.UserSettings
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        fetchSettings()
    }

    private fun fetchSettings() {
        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            try {
                val settings = firestoreRepository.getSettings()
                _uiState.value = SettingsUiState.Success(settings)
            } catch (e: Exception) {
                _uiState.value = SettingsUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    private fun updateSettingsInBackend(updatedSettings: UserSettings) {
        viewModelScope.launch {
            try {
                firestoreRepository.updateSettings(updatedSettings)
                _uiState.value = SettingsUiState.Success(updatedSettings)
            } catch (e: Exception) {
                _uiState.value = SettingsUiState.Error(e.message ?: "Failed to update settings")
            }
        }
    }

    private fun getCurrentSettings(): UserSettings? {
        return if (_uiState.value is SettingsUiState.Success) {
            (_uiState.value as SettingsUiState.Success).settings
        } else {
            null
        }
    }

    fun updateSpendingAlerts(enabled: Boolean) {
        getCurrentSettings()?.let {
            updateSettingsInBackend(it.copy(spendingAlerts = enabled))
        }
    }

    fun updateGoalMilestones(enabled: Boolean) {
        getCurrentSettings()?.let {
            updateSettingsInBackend(it.copy(goalMilestones = enabled))
        }
    }

    fun updateAiSuggestions(enabled: Boolean) {
        getCurrentSettings()?.let {
            updateSettingsInBackend(it.copy(aiSuggestions = enabled))
        }
    }

    fun updateAppUpdates(enabled: Boolean) {
        getCurrentSettings()?.let {
            updateSettingsInBackend(it.copy(appUpdates = enabled))
        }
    }

    fun updateBiometricLock(enabled: Boolean) {
        getCurrentSettings()?.let {
            updateSettingsInBackend(it.copy(biometricLock = enabled))
        }
    }
}
