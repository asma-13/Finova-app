
package com.example.finova.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HuggingFaceService {

    @POST("models/microsoft/DialoGPT-medium")
    suspend fun getChatResponse(
        @Header("Authorization") apiKey: String,
        @Body request: HuggingFaceRequest
    ): HuggingFaceResponse
}
