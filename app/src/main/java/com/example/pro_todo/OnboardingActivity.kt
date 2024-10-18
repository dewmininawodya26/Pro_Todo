package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pro_todo.R

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize UI elements
        val getStartedButton: Button = findViewById(R.id.getStartedButton)
        val loginOption: TextView = findViewById(R.id.loginOption)

        // Navigate to HomeActivity when "Get Started" is clicked
        getStartedButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
