
package com.example.finova.data.model

import com.google.firebase.Timestamp

data class Transaction(
    val id: String = "",
    val name: String = "",
    val date: Timestamp = Timestamp.now(),
    val amount: Double = 0.0,
    val category: String = "",
    val type: String = ""
)
