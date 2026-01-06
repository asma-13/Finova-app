package com.example.finova.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Transaction
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EditTransactionUiState(
    val transaction: Transaction? = null,
    val isLoading: Boolean = true,
    val isTransactionSaved: Boolean = false
)

class EditTransactionViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val transactionId: String = savedStateHandle.get<String>("transactionId")!!

    private val _uiState = MutableStateFlow(EditTransactionUiState())
    val uiState = _uiState.asStateFlow()

    private val _transactionName = MutableStateFlow("")
    val transactionName = _transactionName.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    private val _type = MutableStateFlow("expense")
    val type = _type.asStateFlow()

    init {
        loadTransaction()
    }

    private fun loadTransaction() {
        viewModelScope.launch {
            val transaction = firestoreRepository.getTransaction(transactionId)
            _uiState.value = EditTransactionUiState(transaction = transaction, isLoading = false)
            _transactionName.value = transaction?.name ?: ""
            _amount.value = transaction?.amount?.toString() ?: ""
            _category.value = transaction?.category ?: ""
            _type.value = transaction?.type ?: "expense"
        }
    }

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
            val updatedTransaction = uiState.value.transaction?.copy(
                name = _transactionName.value,
                amount = _amount.value.toDoubleOrNull() ?: 0.0,
                category = _category.value,
                type = _type.value
            )
            if (updatedTransaction != null) {
                firestoreRepository.updateTransaction(updatedTransaction)
                _uiState.value = _uiState.value.copy(isTransactionSaved = true)
            }
        }
    }
}
