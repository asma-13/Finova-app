package com.example.finova.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Goal
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EditGoalUiState(
    val goal: Goal? = null,
    val isLoading: Boolean = true,
    val isGoalSaved: Boolean = false
)

class EditGoalViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val goalId: String = savedStateHandle.get<String>("goalId")!!

    private val _uiState = MutableStateFlow(EditGoalUiState())
    val uiState = _uiState.asStateFlow()

    private val _goalName = MutableStateFlow("")
    val goalName = _goalName.asStateFlow()

    private val _targetAmount = MutableStateFlow("")
    val targetAmount = _targetAmount.asStateFlow()

    init {
        loadGoal()
    }

    private fun loadGoal() {
        viewModelScope.launch {
            val goal = firestoreRepository.getGoal(goalId)
            _uiState.value = EditGoalUiState(goal = goal, isLoading = false)
            _goalName.value = goal?.name ?: ""
            _targetAmount.value = goal?.targetAmount?.toString() ?: ""
        }
    }

    fun onGoalNameChange(newName: String) {
        _goalName.value = newName
    }

    fun onTargetAmountChange(newAmount: String) {
        _targetAmount.value = newAmount
    }

    fun saveGoal() {
        viewModelScope.launch {
            val updatedGoal = uiState.value.goal?.copy(
                name = _goalName.value,
                targetAmount = _targetAmount.value.toDoubleOrNull() ?: 0.0
            )
            if (updatedGoal != null) {
                firestoreRepository.updateGoal(updatedGoal)
                _uiState.value = _uiState.value.copy(isGoalSaved = true)
            }
        }
    }
}
