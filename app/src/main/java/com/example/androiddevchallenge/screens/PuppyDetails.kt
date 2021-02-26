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
package com.example.androiddevchallenge.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.repositories.PuppyVo
import com.example.androiddevchallenge.screens.data.FakePuppies
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PuppyDetails(
    puppyId: String,
    viewModel: PuppyDetailsViewModel = viewModel(),
    close: () -> Unit
) {
    val puppy: PuppyVo? by viewModel.puppy.observeAsState()
    viewModel.loadPuppy(puppyId)

    puppy?.let {
        PuppyDetailsContent(puppy = it, close)
    } ?: CircularProgressIndicator()
}

@Composable
fun PuppyDetailsContent(puppy: PuppyVo, close: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Puppy details: ${puppy.name}")
                },
                navigationIcon = {
                    IconButton(onClick = close) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
            ) {
                CoilImage(
                    data = puppy.image,
                    contentDescription = "${puppy.name} preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth(),
                    loading = {
                        Box(Modifier.matchParentSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = puppy.name,
                        style = MaterialTheme.typography.h4,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "\"${puppy.memePhrase}\"",
                        style = MaterialTheme.typography.h6,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.LocationCity,
                            contentDescription = "location icon",
                            tint = MaterialTheme.colors.secondaryVariant,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            puppy.address,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    )
}

@Preview("Puppy Details", device = Devices.PIXEL_3)
@Composable
fun PuppyDetailsPreview() {
    MyTheme {
        PuppyDetailsContent(
            puppy = FakePuppies.puppy1,
            close = {},
        )
    }
}
