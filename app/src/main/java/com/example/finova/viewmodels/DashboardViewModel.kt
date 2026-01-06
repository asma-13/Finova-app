
package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Goal
import com.example.finova.data.model.Transaction
import com.example.finova.data.model.UserProfile
import com.example.finova.data.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

data class DashboardUiState(
    val userProfile: UserProfile? = null,
    val goals: List<Goal> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true
)

class DashboardViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMockData() // Use this for UI development without Firestore
        // loadDataFromFirestore()
    }

    private fun loadDataFromFirestore() {
        viewModelScope.launch {
            combine(
                firestoreRepository.getUserProfile(),
                firestoreRepository.getGoals(),
                firestoreRepository.getTransactions()
            ) { profile, goals, transactions ->
                DashboardUiState(profile, goals, transactions, isLoading = false)
            }.collect { combinedState ->
                _uiState.value = combinedState
            }
        }
    }

    private fun loadMockData() {
        val mockProfile = UserProfile(
            uid = "123",
            name = "John Doe",
            email = "john.doe@example.com",
            avatarUrl = ""
        )
        val mockGoals = listOf(
            Goal(id = "1", name = "Trip to Bali", targetAmount = 5000.0, savedAmount = 2500.0),
            Goal(id = "2", name = "New Laptop", targetAmount = 2000.0, savedAmount = 1800.0),
            Goal(id = "3", name = "Emergency Fund", targetAmount = 10000.0, savedAmount = 7500.0)
        )
        val mockTransactions = listOf(
            Transaction(name = "Salary", date = Timestamp(Date()), amount = 3000.0, category = "Income", type = "income"),
            Transaction(name = "Rent", date = Timestamp(Date()), amount = 1200.0, category = "Housing", type = "expense"),
            Transaction(name = "Groceries", date = Timestamp(Date()), amount = 250.0, category = "Food", type = "expense"),
            Transaction(name = "Dinner with friends", date = Timestamp(Date()), amount = 80.0, category = "Social", type = "expense"),
            Transaction(name = "Freelance Project", date = Timestamp(Date()), amount = 500.0, category = "Income", type = "income"),
            Transaction(name = "New shoes", date = Timestamp(Date()), amount = 120.0, category = "Shopping", type = "expense")
        )
        _uiState.value = DashboardUiState(
            userProfile = mockProfile,
            goals = mockGoals,
            transactions = mockTransactions,
            isLoading = false
        )
    }
}
