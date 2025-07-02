package com.example.jidan_ai

data class LlamaRequest(
    val model: String = "llama3",
    val prompt: String,
    val stream: Boolean = false
)
