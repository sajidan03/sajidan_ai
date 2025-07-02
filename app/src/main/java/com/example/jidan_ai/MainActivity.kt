package com.example.jidan_ai
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var inputPrompt: EditText
    private lateinit var generateButton: Button
    private lateinit var outputText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputPrompt = findViewById(R.id.et_prompt)
        generateButton = findViewById(R.id.btn_generate)
        outputText = findViewById(R.id.tv_output)

        generateButton.setOnClickListener {
            val promptText = inputPrompt.text.toString()
            generateFromLlama(promptText)
        }
    }

    private fun generateFromLlama(prompt: String) {
        val request = LlamaRequest(prompt = prompt)
        RetrofitClient.instance.generateResponse(request).enqueue(object : Callback<LlamaResponse> {
            override fun onResponse(call: Call<LlamaResponse>, response: Response<LlamaResponse>) {
                if (response.isSuccessful) {
                    outputText.text = response.body()?.response ?: "No response"
                } else {
                    outputText.text = "Failed: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<LlamaResponse>, t: Throwable) {
                outputText.text = "Error: ${t.message}"
            }
        })
    }
}
