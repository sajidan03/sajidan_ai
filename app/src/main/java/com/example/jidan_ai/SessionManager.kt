package com.example.jidan_ai

import android.content.Context

object SessionManager {
    private val SHARED = "SHARED"
    private val TOKEN_KEY = "TOKEN_KEY"
    fun getToken(context: Context): String?{
        val shared = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
        return shared.getString(TOKEN_KEY, null)
    }
    fun putToken(context: Context, token: String){
        val shared = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
        shared.edit().putString(TOKEN_KEY, token).apply()
    }
    fun deleteToken(context: Context, token: String){
        val shared = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
        shared.edit().clear().apply()
    }
}