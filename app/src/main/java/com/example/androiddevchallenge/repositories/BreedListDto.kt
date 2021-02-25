package com.example.androiddevchallenge.repositories

import kotlinx.serialization.Serializable

@Serializable
data class BreedListDto(
    val message: Map<String, List<String>>,
    val status: String,
)
