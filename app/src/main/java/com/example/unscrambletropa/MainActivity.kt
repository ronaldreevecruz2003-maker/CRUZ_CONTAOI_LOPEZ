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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscrambletropa.ui.theme.UnscrambleTropaTheme

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

fun scrambleWord(word: String): String {
    var shuffled = word
    while (shuffled == word) {
        shuffled = word.toList().shuffled().joinToString("")
    }
    return shuffled
}

class GameViewModel : ViewModel() {
    private val words = listOf("CAT", "DOG", "BOOK")

    var currentWordIndex by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    var scrambledWord by mutableStateOf(scrambleWord(words[0]))
        private set

    var feedback by mutableStateOf("")
        private set

    val correctAnswer: String
        get() = words[currentWordIndex]

    val isGameOver: Boolean
        get() = currentWordIndex >= words.size - 1 && feedback.isNotEmpty()

    fun checkAnswer(userAnswer: String) {
        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            score++
            feedback = "Correct!"
        } else {
            feedback = "Wrong! It was $correctAnswer"
        }

        if (currentWordIndex < words.size - 1) {
            currentWordIndex++
            scrambledWord = scrambleWord(words[currentWordIndex])
        }
    }
}

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    var userAnswer by remember { mutableStateOf("") }

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
            text = gameViewModel.scrambledWord,
            fontSize = 40.sp
        )
        Text(
            text = "Unscramble the word!"
        )
        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
            },
            label = {
                Text("Enter your answer")
            }
        )
        Button(
            onClick = {
                gameViewModel.checkAnswer(userAnswer)
                userAnswer = ""
            }
        ) {
            Text("SUBMIT")
        }
        Text(text = gameViewModel.feedback)
        Text(
            text = "Score: ${gameViewModel.score}"
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