package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Feedback
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.Idle)
    val uiState: StateFlow<FeedbackUiState> = _uiState

    fun submitFeedback(message: String, rating: Int) {
        viewModelScope.launch {
            _uiState.value = FeedbackUiState.Loading
            try {
                val feedback = Feedback(
                    message = message,
                    rating = rating
                )
                firestoreRepository.submitFeedback(feedback)
                _uiState.value = FeedbackUiState.Success
            } catch (e: Exception) {
                _uiState.value = FeedbackUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
