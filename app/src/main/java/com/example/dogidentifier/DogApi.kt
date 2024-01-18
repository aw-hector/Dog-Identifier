package com.example.dogidentifier

import com.example.dogidentifier.responses.Dog
import com.example.dogidentifier.responses.DogList
import retrofit2.Response
import retrofit2.http.GET

interface DogApi {
    @GET("list/all")
    suspend fun getAllDogs(): Response<DogList>

    @GET("image/random")
    suspend fun getRandomDog(): Response<Dog>
}