package com.example.gamifiedlearning.model

data class Question(
    val question: String,
    val options: List<String>,
    val answer: Int,
    val level: Int
)
