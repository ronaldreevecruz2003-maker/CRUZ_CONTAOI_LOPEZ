package com.example.ownunscramble

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    val words: List<String> = listOf(
        "CAT",
        "DOG",
        "BOOK"
    )


    private val _uiState = MutableStateFlow(
        GameUiState(
            scrambledWord = words[0]
                .toList()
                .shuffled()
                .joinToString("")
        )
    )


    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
}