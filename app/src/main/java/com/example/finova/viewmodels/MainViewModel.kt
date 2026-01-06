package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.UserProfile
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: android.app.Application) : androidx.lifecycle.AndroidViewModel(application) {
    private val firestoreRepository = FirestoreRepository()
    private val userPreferences = com.example.finova.data.local.UserPreferences(application)
    
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile = _userProfile.asStateFlow()

    init {
        observeUserProfile()
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            userPreferences.userProfileFlow.collect { localProfile ->
                 // Merge local profile (name) with current state or firestore pending state
                 // For now, simply taking the name as authoritative for UI
                _userProfile.value = _userProfile.value.copy(name = localProfile.name)
            }
        }
        
         viewModelScope.launch {
             try {
                firestoreRepository.getUserProfile().collect { profile ->
                    if (profile != null) {
                        // When firestore updates, we might want to update local preferences too in a real app
                        // But here, let's just make sure we don't overwrite local un-synced changes blindly.
                        // For simplicity in this task, let's treat local prefs as "ui override"
                        
                        // If we wanted robust sync:
                        // if (profile.name != localName) { userPreferences.saveName(profile.name) }
                        
                        // For now just update the rest of the profile fields in state
                         _userProfile.value = _userProfile.value.copy(
                             email = profile.email,
                             uid = profile.uid
                         )
                         // Check if name is needed from firestore (e.g. first load and empty local)
                         val localName = userPreferences.getUserProfile().name
                         if (localName.isNullOrEmpty() && !profile.name.isNullOrEmpty()) {
                             userPreferences.saveUserProfile(profile.name ?: "", "")
                         }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
         }
    }
}
