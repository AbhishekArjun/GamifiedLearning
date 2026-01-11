package com.example.gamifiedlearning

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    // UI
    private lateinit var tvQuestion: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvTimer: TextView
    private lateinit var options: List<Button>

    // Game state
    private var currentQuestion = 0
    private var score = 0
    private var mode = GameMode.PRACTICE
    private var level = 1
    private var timer: CountDownTimer? = null

    // MASTER QUESTION BANK
    private val questions = arrayOf(
        "2 + 2 = ?",                    // 0 EASY
        "Capital of India?",            // 1 EASY
        "5 × 3 = ?",                    // 2 MEDIUM
        "Android is based on which kernel?" // 3 HARD
    )

    private val optionsList = arrayOf(
        arrayOf("3", "4", "5", "6"),
        arrayOf("Delhi", "Mumbai", "Kolkata", "Chennai"),
        arrayOf("8", "15", "10", "20"),
        arrayOf("Windows", "Linux", "Mac", "Unix")
    )

    private val correctAnswers = arrayOf(1, 0, 1, 1)

    // LEVEL-WISE QUESTION INDEX
    private val level1Questions = listOf(0, 1) // EASY
    private val level2Questions = listOf(2)    // MEDIUM
    private val level3Questions = listOf(3)    // HARD

    private lateinit var activeQuestions: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // READ MODE & LEVEL
        mode = intent.getStringExtra("MODE") ?: GameMode.PRACTICE
        level = intent.getIntExtra("LEVEL", 1)

        // Bind views
        tvQuestion = findViewById(R.id.tvQuestion)
        tvScore = findViewById(R.id.tvScore)
        tvTimer = findViewById(R.id.tvTimer)

        options = listOf(
            findViewById(R.id.btnOption1),
            findViewById(R.id.btnOption2),
            findViewById(R.id.btnOption3),
            findViewById(R.id.btnOption4)
        )

        // SELECT QUESTIONS BASED ON MODE
        activeQuestions = when (mode) {
            GameMode.PRACTICE -> {
                questions.indices.toList()
            }
            GameMode.CHALLENGE -> {
                questions.indices.shuffled()
            }
            GameMode.LEVEL -> {
                when (level) {
                    1 -> level1Questions
                    2 -> level2Questions
                    3 -> level3Questions
                    else -> level1Questions
                }
            }
            else -> questions.indices.toList()
        }

        loadQuestion()

        options.forEachIndexed { index, button ->
            button.setOnClickListener {
                animateButton(button)
                checkAnswer(index)
            }
        }
    }

    private fun loadQuestion() {
        if (currentQuestion >= activeQuestions.size) {
            Toast.makeText(
                this,
                "🎉 Quiz Finished! Score: $score",
                Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        val qIndex = activeQuestions[currentQuestion]

        tvQuestion.text = questions[qIndex]
        tvScore.text = "Score: $score"

        for (i in options.indices) {
            options[i].text = optionsList[qIndex][i]
        }

        if (mode == GameMode.PRACTICE) {
            tvTimer.text = "Practice Mode"
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(ms: Long) {
                tvTimer.text = "⏱ Time: ${ms / 1000}"
            }

            override fun onFinish() {
                currentQuestion++
                loadQuestion()
            }
        }.start()
    }

    private fun checkAnswer(selectedIndex: Int) {
        timer?.cancel()

        val qIndex = activeQuestions[currentQuestion]

        if (selectedIndex == correctAnswers[qIndex]) {
            score += 10
            Toast.makeText(this, "✅ Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "❌ Wrong!", Toast.LENGTH_SHORT).show()
        }

        currentQuestion++
        loadQuestion()
    }

    private fun animateButton(btn: Button) {
        btn.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(80)
            .withEndAction {
                btn.animate().scaleX(1f).scaleY(1f).duration = 80
            }
    }
}
