package com.example.androiddevchallenge.repositories

import kotlinx.serialization.Serializable

@Serializable
data class DogImageDto(
    val message: String,
    val status: String,
)
