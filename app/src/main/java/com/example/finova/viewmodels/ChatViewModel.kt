package com.example.finova.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finova.data.repository.FirestoreRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isFromUser: Boolean)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false
)

class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()
    private val firestoreRepository = FirestoreRepository()

    // TODO: Add your API key here
    private val apiKey = "AIzaSyAktGJD_W4y1RuAeIu9TEgvk9__B9tvB-M"

    private lateinit var chat: Chat

    init {
        initializeChat()
        welcomeUser()
    }

    private fun initializeChat() {
        val systemPrompt = """
            You are FinovaAI, a helpful and friendly financial assistant for the Finova app.
            Finova is a personal finance management app designed to help users track their expenses, manage budgets, and achieve their financial goals.
            The app was developed by Engr. Asma Channa.
            Your role is to provide financial advice, answer questions about the app's features, and help users with their financial queries.
            Be encouraging, clear, and professional in your responses.
        """.trimIndent()

        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey
        )

        // Start a chat with the system prompt to give the AI its persona.
        chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text(systemPrompt) },
                content(role = "model") { text("Understood. I am FinovaAI, ready to assist.") }
            )
        )
    }

    private fun welcomeUser() {
        viewModelScope.launch {
            val user = firestoreRepository.getUserProfile().first()
            val userName = user?.name?.substringBefore(" ") ?: "there"
            val welcomeMessage = ChatMessage(
                text = "Welcome $userName. How can I assist you today?",
                isFromUser = false
            )

            // Add the welcome message to the chat screen
            _uiState.value = _uiState.value.copy(
                messages = _uiState.value.messages + welcomeMessage
            )
        }
    }

    fun clearChat() {
        _uiState.value = ChatUiState() // Reset the UI state
        initializeChat() // Re-initialize the chat to restore the system prompt
        welcomeUser() // Show the welcome message again
    }

    fun sendMessage(messageText: String) {
        val userMessage = ChatMessage(messageText, true)

        // Add the user's message to the UI immediately
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + userMessage,
            isLoading = true
        )

        viewModelScope.launch {
            try {
                // Send the message to the generative model
                val response = chat.sendMessage(messageText)
                val botMessage = ChatMessage(response.text ?: "Sorry, I couldn't process that.", false)

                // Add the bot's response to the UI
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + botMessage,
                    isLoading = false
                )
            } catch (e: Exception) {
                val errorMessage = ChatMessage("Error: ${e.message}", false)
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + errorMessage,
                    isLoading = false
                )
            }
        }
    }
}
