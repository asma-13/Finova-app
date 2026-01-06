package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Transaction
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TransactionsUiState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class TransactionsViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val _uiState = MutableStateFlow(TransactionsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                firestoreRepository.getTransactions().collect { transactions ->
                    _uiState.value = TransactionsUiState(transactions = transactions, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "Failed to load transactions")
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            firestoreRepository.deleteTransaction(transaction)
        }
    }
}
