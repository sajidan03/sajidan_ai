package com.example.jidan_ai

import android.os.Bundle
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var inputPrompt: EditText
    private lateinit var generateButton: AppCompatButton
    private lateinit var outputText: TextView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputPrompt = findViewById(R.id.et_prompt)
        generateButton = findViewById(R.id.btn_generate)
        outputText = findViewById(R.id.tv_output)
        scrollView = findViewById(R.id.scrollView)

        generateButton.setOnClickListener {
            val promptText = inputPrompt.text.toString().trim()
            if (promptText.isNotEmpty()) {
                generateFromLlama(promptText)
                inputPrompt.text.clear()
            }
        }
    }

    private fun generateFromLlama(prompt: String) {
        outputText.append("\n\n🧠 Pertanyaan: $prompt\n⏳ Memproses...")
        generateButton.isEnabled = false

        val request = LlamaRequest(prompt = prompt)
        RetrofitClient.instance.generateResponse(request).enqueue(object : Callback<LlamaResponse> {
            override fun onResponse(call: Call<LlamaResponse>, response: Response<LlamaResponse>) {
                generateButton.isEnabled = true
                val jawaban = response.body()?.response ?: "Tidak ada jawaban"
                outputText.text = outputText.text.removeSuffix("⏳ Memproses...")
                outputText.append("\n💬 Jawaban: $jawaban")
                scrollView.post {
                    scrollView.fullScroll(View.FOCUS_DOWN)
                }
            }

            override fun onFailure(call: Call<LlamaResponse>, t: Throwable) {
                generateButton.isEnabled = true
                outputText.append("\n❌ Error: ${t.message}")
                scrollView.post {
                    scrollView.fullScroll(View.FOCUS_DOWN)
                }
            }
        })
    }
}
