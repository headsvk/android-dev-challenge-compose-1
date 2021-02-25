package com.example.androiddevchallenge.repositories

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {

    @GET("breed/{breed}/images/random")
    suspend fun image(@Path("breed") breed: String): DogImageDto

    @GET("breed/{breed}/{sub-breed}/images/random")
    suspend fun image(
        @Path("breed") breed: String,
        @Path("sub-breed") subBreed: String,
    ): DogImageDto

    @GET("breeds/list/all")
    suspend fun breeds(): BreedListDto
}
