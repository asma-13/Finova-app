package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Transaction
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddTransactionUiState(
    val isTransactionSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AddTransactionViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState = _uiState.asStateFlow()

    private val _transactionName = MutableStateFlow("")
    val transactionName = _transactionName.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    private val _type = MutableStateFlow("expense")
    val type = _type.asStateFlow()

    fun onTransactionNameChange(newName: String) {
        _transactionName.value = newName
    }

    fun onAmountChange(newAmount: String) {
        _amount.value = newAmount
    }

    fun onCategoryChange(newCategory: String) {
        _category.value = newCategory
    }

    fun onTypeChange(newType: String) {
        _type.value = newType
    }

    fun saveTransaction() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val transaction = Transaction(
                    name = _transactionName.value,
                    amount = _amount.value.toDoubleOrNull() ?: 0.0,
                    category = _category.value,
                    type = _type.value
                )
                firestoreRepository.addTransaction(transaction)
                _uiState.value = AddTransactionUiState(isTransactionSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Failed to save transaction")
            }
        }
    }
}
