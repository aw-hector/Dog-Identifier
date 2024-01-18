package com.example.dogidentifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnswerView(
    answer: Answer,
    onClick: () -> Unit
) {
    val backgroundColor = when (answer.state) {
        AnswerState.UNSELECTED -> Color.Transparent
        AnswerState.CORRECT -> Color.Green
        AnswerState.INCORRECT -> Color.Red
    }

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(10.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = answer.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.weight(1f))

        if (answer.state == AnswerState.CORRECT) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
        }
        if (answer.state == AnswerState.INCORRECT) {
            Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
        }
    }
}

enum class AnswerState {
    UNSELECTED,
    CORRECT,
    INCORRECT
}