package com.example.gamifiedlearning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val name = findViewById<EditText>(R.id.etUsername)
        val btn = findViewById<Button>(R.id.btnLogin)

        btn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("username", name.text.toString())
            startActivity(intent)
        }
    }
}
