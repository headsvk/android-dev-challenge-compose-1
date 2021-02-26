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
package com.example.androiddevchallenge.screens.data

import com.example.androiddevchallenge.repositories.PuppyVo

object FakePuppies {
    val puppy1 = PuppyVo(
        id = "1",
        name = "Buster",
        breed = "English Springer",
        sound = "harsh",
        memePhrase = "smol pupperino",
        age = "adult",
        address = "Copenhagen",
        coatLength = "5cm",
        size = "60cm",
        image = "https://images.dog.ceo/breeds/springer-english/n02102040_1230.jpg",
    )
}
