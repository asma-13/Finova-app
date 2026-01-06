package com.example.finova.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.data.model.Feedback
import com.example.finova.data.repository.FirestoreRepository
import com.example.finova.ui.components.StyledTextField
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon
import kotlinx.coroutines.launch

@Composable
fun FeedbackScreen(navController: NavController) {
    var feedbackText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val firestoreRepository = remember { FirestoreRepository() }
    var isLoading by remember { mutableStateOf(false) }

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "We value your feedback",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaCream,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            StyledTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                label = "Tell us what you think...",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (feedbackText.isNotBlank()) {
                        scope.launch {
                            isLoading = true
                            try {
                                firestoreRepository.submitFeedback(Feedback(message = feedbackText))
                                Toast.makeText(navController.context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } catch (e: Exception) {
                                Toast.makeText(navController.context, "Failed to submit: ${e.message}", Toast.LENGTH_SHORT).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && feedbackText.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = FinovaCream)
                } else {
                    Text("Submit Feedback")
                }
            }
        }
    }
}
