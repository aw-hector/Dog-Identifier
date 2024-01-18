package com.example.dogidentifier

import com.example.dogidentifier.responses.Dog
import com.example.dogidentifier.responses.DogDeserializer
import com.example.dogidentifier.responses.DogList
import com.example.dogidentifier.responses.DogListDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val baseUrl = "https://dog.ceo/api/breeds/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(DogList::class.java, DogListDeserializer())
                        .registerTypeAdapter(Dog::class.java, DogDeserializer())
                        .create()
                )
            )
            .build()
    }
}