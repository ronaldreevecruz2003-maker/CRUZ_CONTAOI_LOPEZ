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

@Composable
fun GameScreen() {
    var userAnswer by remember {
        mutableStateOf(&quot;&quot;)
    }
    val words = listOf(
        &quot;CAT&quot;,
    &quot;DOG&quot;,
    &quot;BOOK&quot;
    )
    var currentWordIndex by remember {
        mutableStateOf(0)
    }
    val correctAnswer = words[currentWordIndex]
    var score by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = &quot;UNSCRAMBLE&quot;,
        fontSize = 30.sp
        )
        Text(
            text = correctAnswer,

            fontSize = 40.sp
        )
        Text(
            text = &quot;Unscramble the word!&quot;
        )
        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
            },
            label = {
                Text(&quot;Enter your answer&quot;)
            }
        )
        Button(
            onClick = {
                if (userAnswer == correctAnswer) {
                    score++
                    if (currentWordIndex &lt; words.size - 1) {
                        currentWordIndex++
                        userAnswer = &quot;&quot;
                    }
                }
            }
        ) {
            Text(&quot;SUBMIT&quot;)
        }
        Text(
            text = &quot;Score: $score&quot;
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