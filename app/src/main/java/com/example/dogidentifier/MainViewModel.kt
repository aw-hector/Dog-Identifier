package com.example.dogidentifier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogidentifier.responses.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val dogApi: DogApi by lazy {
        RetrofitHelper.getInstance().create(DogApi::class.java)
    }

    private var dogs: List<String> = mutableListOf()
    private var rightAnswer: String? by mutableStateOf(null)

    var imageUrl: String? by mutableStateOf(null)
    var answers: List<Answer> by mutableStateOf(emptyList())
    var error: String? by mutableStateOf(null)

    fun loadNextDogAndAnswers() {
        viewModelScope.launch {
            if (dogs.isEmpty()) {
                val dogsResponse = dogApi.getAllDogs()
                if (dogsResponse.isSuccessful) {
                    dogsResponse.body()?.breeds?.let {
                        dogs = it
                    }
                    loadNextDogAndAnswers()
                } else {
                    error = dogsResponse.errorBody()?.string()
                }
            } else {
                val dogResponse = dogApi.getRandomDog()
                if (dogResponse.isSuccessful) {
                    imageUrl = dogResponse.body()?.imageUrl
                    dogResponse.body()?.let {
                        rightAnswer = it.breed
                        answers = getAnswersFrom(it, dogs)
                    }
                } else {
                    error = dogResponse.errorBody()?.string()
                }
            }
        }
    }

    fun chooseAnswer(answer: Answer) {
        answers = answers.map {
            if (it.title == rightAnswer) {
                Answer(title = it.title, state = AnswerState.CORRECT)
            } else {
                Answer(
                    title = it.title, state = if (it.title == answer.title) {
                        AnswerState.INCORRECT
                    } else {
                        AnswerState.UNSELECTED
                    }
                )
            }
        }
    }

    private fun getAnswersFrom(dog: Dog, dogList: List<String>): List<Answer> {
        val allAnswers = dogList.filterNot { it == dog.breed }
        val firstAnswer = allAnswers.random()
        val secondAnswer = allAnswers.filterNot { it == firstAnswer }.random()
        return listOf(dog.breed, firstAnswer, secondAnswer).shuffled().map {
            Answer(title = it, state = AnswerState.UNSELECTED)
        }
    }
}