
package com.example.finova.data.repository

import com.example.finova.network.ConversationInputs
import com.example.finova.network.HuggingFaceRequest
import com.example.finova.network.HuggingFaceResponse
import com.example.finova.network.RetrofitClient

class HuggingFaceRepository {

    private val huggingFaceService = RetrofitClient.huggingFaceService

    suspend fun getChatResponse(history: List<Pair<String, String>>, newMessage: String): HuggingFaceResponse {
        val pastUserInputs = history.map { it.first }
        val generatedResponses = history.map { it.second }

        val request = HuggingFaceRequest(
            inputs = ConversationInputs(
                past_user_inputs = pastUserInputs,
                generated_responses = generatedResponses,
                text = newMessage
            )
        )

        return huggingFaceService.getChatResponse(
            apiKey = "Bearer ${RetrofitClient.HUGGING_FACE_API_KEY}",
            request = request
        )
    }
}
