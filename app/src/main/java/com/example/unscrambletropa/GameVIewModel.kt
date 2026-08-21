package com.example.ownunscramble

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    val words: List<String> = listOf(
        "CAT",
        "DOG",
        "BOOK"
    )


    var currentWordIndex = 0
    var score = 0
    var userAnswer = ""
}