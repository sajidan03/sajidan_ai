package com.example.jidan_ai

data class LlamaResponse(
    val model: String?,
    val created_at: String?,
    val response: String?,
    val done: Boolean?
)
