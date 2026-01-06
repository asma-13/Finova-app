package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Goal
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddGoalUiState(
    val isGoalSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AddGoalViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow(AddGoalUiState())
    val uiState = _uiState.asStateFlow()

    private val _goalName = MutableStateFlow("")
    val goalName = _goalName.asStateFlow()

    private val _targetAmount = MutableStateFlow("")
    val targetAmount = _targetAmount.asStateFlow()

    fun onGoalNameChange(newName: String) {
        _goalName.value = newName
    }

    fun onTargetAmountChange(newAmount: String) {
        _targetAmount.value = newAmount
    }

    fun saveGoal() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val goal = Goal(
                    name = _goalName.value,
                    targetAmount = _targetAmount.value.toDoubleOrNull() ?: 0.0
                )
                firestoreRepository.addGoal(goal)
                _uiState.value = AddGoalUiState(isGoalSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Failed to save goal")
            }
        }
    }
}
