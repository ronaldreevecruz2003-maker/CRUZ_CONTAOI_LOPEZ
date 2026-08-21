package com.example.unscrambletropa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscrambletropa.ui.theme.UnscrambleTropaTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnscrambleTropaTheme {
                GameScreen()
            }
        }
    }
}

data class GameUiState(
    val scrambledWord: String = "",
    val userAnswer: String = "",
    val score: Int = 0,
    val isGuessWrong: Boolean = false
)

class GameViewModel : ViewModel() {
    private val words: List<String> = listOf("CAT", "DOG", "BOOK")
    private var currentWordIndex = 0

    private val _uiState = MutableStateFlow(
        GameUiState(scrambledWord = shuffleWord(words[0]))
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun shuffleWord(word: String): String {
        val letters = word.toList()
        var shuffled: List<Char>
        do {
            shuffled = letters.shuffled()
        } while (shuffled.zip(letters).any { it.first == it.second })
        return shuffled.joinToString("")
    }

    fun updateUserAnswer(answer: String) {
        _uiState.value = _uiState.value.copy(
            userAnswer = answer,
            isGuessWrong = false
        )
    }

    fun checkAnswer() {
        val currentWord = words[currentWordIndex]
        val isCorrect = _uiState.value.userAnswer.equals(currentWord, ignoreCase = true)

        if (isCorrect) {
            currentWordIndex = (currentWordIndex + 1) % words.size
            _uiState.value = _uiState.value.copy(
                score = _uiState.value.score + 1,
                userAnswer = "",
                isGuessWrong = false,
                scrambledWord = shuffleWord(words[currentWordIndex])
            )
        } else {
            _uiState.value = _uiState.value.copy(isGuessWrong = true)
        }
    }
}

@Composable
fun GameScreen() {
    val viewModel: GameViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "UNSCRAMBLE",
            fontSize = 30.sp
        )
        Text(
            text = uiState.scrambledWord,
            fontSize = 40.sp
        )
        Text(
            text = "Unscramble the word!"
        )
        OutlinedTextField(
            value = uiState.userAnswer,
            onValueChange = { viewModel.updateUserAnswer(it) },
            isError = uiState.isGuessWrong,
            label = {
                Text(if (uiState.isGuessWrong) "Wrong guess, try again" else "Enter your answer")
            }
        )
        Button(
            onClick = { viewModel.checkAnswer() }
        ) {
            Text("SUBMIT")
        }
        Text(
            text = "Score : ${uiState.score}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTropaTheme {
        GameScreen()
    }
}