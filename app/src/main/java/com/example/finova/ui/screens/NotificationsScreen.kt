package com.example.finova.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon

@Composable
fun NotificationsScreen(navController: NavController) {
    val notifications = listOf(
        NotificationItem("Goal Achieved!", "You reached your 'New Laptop' goal.", "2 mins ago"),
        NotificationItem("New Expense", "Lunch at Cafe added.", "1 hour ago"),
        NotificationItem("Security Alert", "New login from Windows PC.", "Yesterday"),
        NotificationItem("Budget Exceeded", "You exceeded your dining budget.", "2 days ago")
    )

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaCream,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(notifications) { notification ->
                    NotificationCard(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = FinovaCream,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = notification.title, style = MaterialTheme.typography.titleMedium, color = FinovaCream)
                Text(text = notification.message, style = MaterialTheme.typography.bodyMedium, color = FinovaCream.copy(alpha = 0.7f))
                Text(text = notification.time, style = MaterialTheme.typography.labelSmall, color = FinovaCream.copy(alpha = 0.5f))
            }
        }
    }
}

data class NotificationItem(val title: String, val message: String, val time: String)
