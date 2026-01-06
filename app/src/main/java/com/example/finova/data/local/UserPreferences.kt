package com.example.finova.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.finova.data.model.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "finova_user_prefs"
        private const val KEY_NAME = "key_name"
        private const val KEY_DOB = "key_dob"
        private const val KEY_EMAIL = "key_email"
    }

    fun saveUserProfile(name: String, dob: String) {
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_DOB, dob)
            .apply()
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            name = sharedPreferences.getString(KEY_NAME, "") ?: "",
            // Email might be managed by Auth, but we can store it locally if needed.
            // For now, we mainly focus on Name as per request.
        )
    }

    val userProfileFlow: Flow<UserProfile> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_NAME || key == KEY_DOB) {
                trySend(
                    UserProfile(
                        name = prefs.getString(KEY_NAME, "") ?: ""
                    )
                )
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.onStart {
        emit(getUserProfile())
    }
}
