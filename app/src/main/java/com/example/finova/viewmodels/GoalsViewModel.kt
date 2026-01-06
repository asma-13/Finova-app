package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Goal
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GoalsUiState(
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class GoalsViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val _uiState = MutableStateFlow(GoalsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            try {
                firestoreRepository.getGoals().collect { goals ->
                    _uiState.value = GoalsUiState(goals = goals, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Failed to load goals")
            }
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            firestoreRepository.deleteGoal(goal)
        }
    }
}
