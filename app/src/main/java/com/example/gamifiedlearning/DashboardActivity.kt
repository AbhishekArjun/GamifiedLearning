package com.example.gamifiedlearning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnPractice = findViewById<Button>(R.id.btnPractice)
        val btnChallenge = findViewById<Button>(R.id.btnChallenge)
        val btnLevel1 = findViewById<Button>(R.id.btnLevel1)

        // 🧠 PRACTICE MODE
        btnPractice.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("MODE", GameMode.PRACTICE)
            startActivity(intent)
        }

        // ⚡ CHALLENGE MODE
        btnChallenge.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("MODE", GameMode.CHALLENGE)
            startActivity(intent)
        }

        // 🏆 LEVEL MODE – LEVEL 1
        btnLevel1.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("MODE", GameMode.LEVEL)
            intent.putExtra("LEVEL", 1)
            startActivity(intent)
        }
    }
}
