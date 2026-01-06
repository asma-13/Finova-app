package com.example.finova.viewmodels

import com.example.finova.data.model.UserProfile

sealed interface EditProfileUiState {
    object Loading : EditProfileUiState
    data class Success(val userProfile: UserProfile) : EditProfileUiState
    data class Error(val message: String) : EditProfileUiState
}
