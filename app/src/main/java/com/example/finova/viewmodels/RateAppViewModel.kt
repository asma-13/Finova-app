
package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.model.Feedback
import com.example.finova.data.repository.FirestoreRepository
import kotlinx.coroutines.launch

class RateAppViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    fun onSubmitRating(rating: Int, message: String = "") {
        if (rating > 0) {
            viewModelScope.launch {
                val feedback = Feedback(
                    rating = rating,
                    message = message
                )
                firestoreRepository.submitFeedback(feedback)
            }
        }
    }
}
