package com.example.jidan_ai

data class LlamaRequest(
    val model: String = "gemma",
    val prompt: String,
    val stream: Boolean = false
)
