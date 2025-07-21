package com.example.jidan_ai

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://192.168.110.90:11434"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private fun getInstance(context: Context, token: String): ApiService{
        val client = OkHttpClient.Builder()
            .addInterceptor {
                chain->
                val origin = chain.request()
                val token = SessionManager.getToken(context)
                val newBuilder = origin.newBuilder()
                if (!token.isNullOrEmpty()){
                    newBuilder.addHeader("Authorization", "Bearer $token")
                }
                val request = newBuilder.build()
                chain.proceed(request)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        return retrofit.crea
    }

}
