package com.example.finova.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaGold
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.viewmodels.ChatMessage
import com.example.finova.viewmodels.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, chatViewModel: ChatViewModel = viewModel()) {
    val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()
    var messageText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(FinovaMaroon)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "FinovaAI",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaGold
            )
            IconButton(onClick = { chatViewModel.clearChat() }) {
                Icon(Icons.Default.Delete, contentDescription = "Clear Chat", tint = FinovaGold)
            }
        }

        // Chat messages
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(uiState.messages.reversed()) { message ->
                ChatBubble(message = message)
            }
        }

        // Loading indicator
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp).align(Alignment.CenterHorizontally))
        }

        // Input field
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask FinovaAI...") },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { 
                if (messageText.isNotBlank()) {
                    chatViewModel.sendMessage(messageText)
                    messageText = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = FinovaGold)
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isFromUser) FinovaGold else Color.White
    val textColor = if (message.isFromUser) FinovaMaroon else Color.Black
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val shape = if (message.isFromUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomEnd = 16.dp, bottomStart = 16.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Column(horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(bubbleColor)
                    .padding(12.dp)
            ) {
                Text(text = message.text, color = textColor)
            }
            Text(
                text = "Just now", // Placeholder for real time
                style = MaterialTheme.typography.labelSmall,
                color = FinovaCream.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
