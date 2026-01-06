package com.example.finova.data.model

data class UserSettings(
    val spendingAlerts: Boolean = false,
    val goalMilestones: Boolean = false,
    val aiSuggestions: Boolean = false,
    val appUpdates: Boolean = false,
    val biometricLock: Boolean = false
)
