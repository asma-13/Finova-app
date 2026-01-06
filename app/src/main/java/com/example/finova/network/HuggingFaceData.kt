
package com.example.finova.network

// Request Body
data class HuggingFaceRequest(
    val inputs: ConversationInputs
)

data class ConversationInputs(
    val past_user_inputs: List<String>,
    val generated_responses: List<String>,
    val text: String
)

// Response Body
data class HuggingFaceResponse(
    val generated_text: String
)
