package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.UserProfile
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class EditProfileScreenUiState(
    val isLoading: Boolean = true,
    val isProfileSaved: Boolean = false,
    val userProfile: UserProfile? = null
)

class EditProfileViewModel(application: android.app.Application) : androidx.lifecycle.AndroidViewModel(application) {

    private val firestoreRepository = FirestoreRepository()
    private val userPreferences = com.example.finova.data.local.UserPreferences(application)

    private val _uiState = MutableStateFlow(EditProfileScreenUiState())
    val uiState: StateFlow<EditProfileScreenUiState> = _uiState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Load from local prefs first for immediate feedback
            val localProfile = userPreferences.getUserProfile()
            _name.value = localProfile.name ?: ""

            // Optionally still fetch from Firestore to sync, but prioritize local for now as per request
             try {
                val profile = firestoreRepository.getUserProfile().first()
                 if (profile != null) {
                     // Only overwrite if local is empty? Or simple logic: use Firestore if available
                     // For this task, "load it everywhere" implies local is source of truth for edits
                     if (_name.value.isEmpty()) {
                         _name.value = profile.name ?: ""
                     }
                     _uiState.value = _uiState.value.copy(userProfile = profile, isLoading = false)
                 } else {
                      _uiState.value = _uiState.value.copy(isLoading = false)
                 }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onDobChange(newDob: String) {
        _dob.value = newDob
    }

    fun saveProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Save locally
                userPreferences.saveUserProfile(_name.value, _dob.value)

                // Save to Firestore
                val currentProfile = _uiState.value.userProfile ?: UserProfile()
                val updatedProfile = currentProfile.copy(
                    name = _name.value
                )
                firestoreRepository.updateUserProfile(updatedProfile)
                
                _uiState.value = _uiState.value.copy(isProfileSaved = true, isLoading = false)
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(isLoading = false)
                e.printStackTrace()
            }
        }
    }
}
