package com.example.jidan_ai

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/api/generate")
    fun generateResponse(@Body request: LlamaRequest): Call<LlamaResponse>
}