
package com.example.finova.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    suspend fun createUser(email: String, pass: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, pass).await()
    }

    suspend fun signIn(email: String, pass: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, pass).await()
    }

    suspend fun googleSignIn(credential: AuthCredential): AuthResult {
        return auth.signInWithCredential(credential).await()
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun reauthenticateAndChangePassword(currentPass: String, newPass: String) {
        val user = auth.currentUser!!
        val credential = EmailAuthProvider.getCredential(user.email!!, currentPass)
        user.reauthenticate(credential).await()
        user.updatePassword(newPass).await()
    }
}
