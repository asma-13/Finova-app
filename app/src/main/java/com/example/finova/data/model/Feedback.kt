
package com.example.finova.data.model

import com.google.firebase.Timestamp

data class Feedback(
    val userId: String = "",
    val message: String = "",
    val rating: Int = 0,
    val timestamp: Timestamp = Timestamp.now()
)
