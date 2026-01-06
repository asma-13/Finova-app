package com.example.finova.viewmodels

sealed interface FeedbackUiState {
    object Idle : FeedbackUiState
    object Loading : FeedbackUiState
    object Success : FeedbackUiState
    data class Error(val message: String) : FeedbackUiState
}
