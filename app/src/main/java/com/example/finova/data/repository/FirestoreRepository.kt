package com.example.finova.data.repository

import com.example.finova.data.model.Feedback
import com.example.finova.data.model.Goal
import com.example.finova.data.model.Transaction
import com.example.finova.data.model.UserProfile
import com.example.finova.data.model.UserSettings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private val authRepository = AuthRepository()

    private fun getUserId(): String? = authRepository.getCurrentUserId()

    fun getUserProfile(): Flow<UserProfile?> {
        return getUserId()?.let {
            db.collection("users").document(it).collection("profile")
                .document("profile").snapshots().map { snapshot ->
                    snapshot.toObject(UserProfile::class.java)
                }
        } ?: emptyFlow()
    }

    suspend fun updateUserProfile(userProfile: UserProfile) {
        getUserId()?.let {
            db.collection("users").document(it).collection("profile").document("profile")
                .set(userProfile).await()
        }
    }

    fun getGoals(): Flow<List<Goal>> {
        return getUserId()?.let {
            db.collection("users").document(it).collection("goals")
                .snapshots().map { snapshot ->
                    snapshot.documents.mapNotNull { doc -> doc.toObject(Goal::class.java)?.copy(id = doc.id) }
                }
        } ?: emptyFlow()
    }

    suspend fun getGoal(goalId: String): Goal? {
        return getUserId()?.let {
            db.collection("users").document(it).collection("goals").document(goalId).get().await()
                .toObject(Goal::class.java)?.copy(id = goalId)
        }
    }

    suspend fun addGoal(goal: Goal) {
        getUserId()?.let {
            val docRef = db.collection("users").document(it).collection("goals").document()
            docRef.set(goal.copy(id = docRef.id)).await()
        }
    }

    suspend fun updateGoal(goal: Goal) {
        getUserId()?.let {
            db.collection("users").document(it).collection("goals").document(goal.id).set(goal).await()
        }
    }

    suspend fun deleteGoal(goal: Goal) {
        getUserId()?.let {
            db.collection("users").document(it).collection("goals").document(goal.id).delete().await()
        }
    }

    fun getTransactions(): Flow<List<Transaction>> {
        return getUserId()?.let {
            db.collection("users").document(it).collection("transactions")
                .snapshots().map { snapshot ->
                    snapshot.documents.mapNotNull { doc -> doc.toObject(Transaction::class.java)?.copy(id = doc.id) }
                }
        } ?: emptyFlow()
    }

    suspend fun getTransaction(transactionId: String): Transaction? {
        return getUserId()?.let {
            db.collection("users").document(it).collection("transactions").document(transactionId).get().await()
                .toObject(Transaction::class.java)?.copy(id = transactionId)
        }
    }

    suspend fun addTransaction(transaction: Transaction) {
        getUserId()?.let {
            val docRef = db.collection("users").document(it).collection("transactions").document()
            docRef.set(transaction.copy(id = docRef.id)).await()
        }
    }

    suspend fun updateTransaction(transaction: Transaction) {
        getUserId()?.let {
            db.collection("users").document(it).collection("transactions").document(transaction.id).set(transaction).await()
        }
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        getUserId()?.let {
            db.collection("users").document(it).collection("transactions").document(transaction.id).delete().await()
        }
    }

    suspend fun getSettings(): UserSettings? {
        return getUserId()?.let {
            db.collection("users").document(it).collection("settings").document("settings").get().await()
                .toObject(UserSettings::class.java)
        }
    }

    suspend fun updateSettings(userSettings: UserSettings) {
        getUserId()?.let {
            db.collection("users").document(it).collection("settings").document("settings").set(userSettings).await()
        }
    }

    suspend fun submitFeedback(feedback: Feedback) {
        getUserId()?.let {
            db.collection("feedback").add(feedback.copy(userId = it)).await()
        }
    }
}
