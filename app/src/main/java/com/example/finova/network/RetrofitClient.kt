
package com.example.finova.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val HUGGING_FACE_API_KEY = "YOUR_API_KEY_HERE"

    private const val BASE_URL = "https://api-inference.huggingface.co/"

    val huggingFaceService: HuggingFaceService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HuggingFaceService::class.java)
    }
}
