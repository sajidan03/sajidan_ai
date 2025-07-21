package com.example.jidan_ai

data class LoginResponse(
    val message: String,
    val user: User?,
    val token: String,
)
data class User(
    val id : Int,
    val username: String,
    val role: String,
    val address: String,
    val phone: String,
    val created_at : String,
    val updated_at : String,
)
