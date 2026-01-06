
package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Goal
import com.example.finova.data.model.UserProfile
import com.example.finova.data.repository.FirestoreRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class ProfileUiState(
    val userProfile: UserProfile? = null,
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = true
)

class ProfileViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                firestoreRepository.getUserProfile(),
                firestoreRepository.getGoals()
            ) { profile, goals ->
                ProfileUiState(profile, goals, isLoading = false)
            }.collect { combinedState ->
                _uiState.value = combinedState
            }
        }
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}
