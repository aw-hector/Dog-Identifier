package com.example.dogidentifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(key1 = viewModel) {
                viewModel.loadNextDogAndAnswers()
            }

            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = resources.getString(R.string.game_title),
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                AsyncImage(
                    model = viewModel.imageUrl,
                    contentDescription = "Some dog",
                    modifier = Modifier.height(300.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                viewModel.answers.forEach {
                    AnswerView(answer = it) {
                        viewModel.chooseAnswer(answer = it)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { viewModel.loadNextDogAndAnswers() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = resources.getString(R.string.next_button_title))
                }
            }
        }
    }
}