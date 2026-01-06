package com.example.finova.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon

@Composable
fun HelpScreen(navController: NavController) {
    val faqs = listOf(
        FAQItem("How do I add a goal?", "Go to the Goals tab and tap the + button."),
        FAQItem("Is my data secure?", "Yes, we use industry-standard encryption."),
        FAQItem("Can I export my data?", "Currently this feature is in development."),
        FAQItem("How do I reset my password?", "Go to Profile > Change Password.")
    )

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Help & Support",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaCream,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(faqs) { faq ->
                    FAQCard(faq)
                }
            }
        }
    }
}

@Composable
fun FAQCard(faq: FAQItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = faq.question, style = MaterialTheme.typography.titleMedium, color = FinovaCream, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = faq.answer, style = MaterialTheme.typography.bodyMedium, color = FinovaCream.copy(alpha = 0.8f))
        }
    }
}

data class FAQItem(val question: String, val answer: String)
