/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create
import java.util.Locale

object PuppyRepository {
    private const val PUPPY_COUNT = 20

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()

    private val faker = Faker()
    private val dogApi: DogApi = retrofit.create()
    private var puppies: List<PuppyVo>? = null

    suspend fun listPuppies(): List<PuppyVo> {
        return puppies ?: run {
            coroutineScope {
                List(PUPPY_COUNT) { id ->
                    async {
                        createPuppy(id.toString())
                    }
                }.map { it.await() }
            }.also { puppies = it }
        }
    }

    private suspend fun createPuppy(id: String): PuppyVo {
        val dog = faker.dog
        val breedType = dogApi.breeds().message.entries.random()
        val breedTypeName = breedType.key.capitalize(Locale.ENGLISH)
        val breedName: String
        val image = if (breedType.value.isNotEmpty()) {
            val breedSubtype = breedType.value.random()
            breedName = "${breedSubtype.capitalize(Locale.ENGLISH)} $breedTypeName"
            dogApi.image(breedType.key, breedSubtype).message
        } else {
            breedName = breedTypeName
            dogApi.image(breedType.key).message
        }

        return PuppyVo(
            id = id,
            name = dog.name(),
            breed = breedName,
            sound = dog.sound(),
            memePhrase = dog.memePhrase(),
            age = dog.age(),
            address = faker.address.city(),
            coatLength = dog.coatLength(),
            size = dog.size(),
            image = image,
        )
    }

    suspend fun getPuppy(id: String): PuppyVo {
        // This would be a database call
        return puppies!!.first { it.id == id }
    }
}
