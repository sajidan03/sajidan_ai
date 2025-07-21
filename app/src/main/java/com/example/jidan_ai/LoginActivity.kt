package com.example.jidan_ai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val token = SessionManager.getToken(applicationContext)
        val login = findViewById<AppCompatButton>(R.id.login)
        login.setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()
            val loginRequest = LoginRequest(user, pass)
            ApiClient.getInstance(applicationContext, token.toString()).login(loginRequest).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        Log.d("Error login", "$response")
                        val errorBody = response.errorBody()?.string()
                        Log.e("Login Error", "Code: ${response.code()} Body: $errorBody")

                        if (response.isSuccessful){
                            Log.d("Error login", "$response")
                            SessionManager.putToken(applicationContext, token.toString())
                            Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                        if (!response.isSuccessful){
                            Toast.makeText(this@LoginActivity, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                            Log.d("Error login", "$response")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                        Log.d("Error login", "$t")
                    }
                })
        }

    }
}
