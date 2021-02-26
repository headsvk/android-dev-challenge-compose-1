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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.repositories.PuppyVo
import com.example.androiddevchallenge.screens.data.FakePuppies
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PuppyList(viewModel: PuppyListViewModel = viewModel(), openDetails: (puppy: PuppyVo) -> Unit) {
    val puppies: List<PuppyVo> by viewModel.itemList.observeAsState(emptyList())
    PuppyListContent(puppies, openDetails)
}

@Composable
fun PuppyListContent(puppies: List<PuppyVo>, openDetails: (puppy: PuppyVo) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
            )
        },
        content = {
            if (puppies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading puppies",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(puppies) { puppy ->
                        PuppyListItem(puppy, openDetails)
                    }
                }
            }
        }
    )
}

@Composable
fun PuppyListItem(puppy: PuppyVo, openDetails: (puppy: PuppyVo) -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = { openDetails(puppy) }),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            CoilImage(
                data = puppy.image,
                contentDescription = "${puppy.name} preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(60.dp)),
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = puppy.name,
                        style = MaterialTheme.typography.h6,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = puppy.views,
                        style = MaterialTheme.typography.body2,
                    )
                    Icon(
                        Icons.Outlined.Visibility,
                        contentDescription = "views",
                        tint = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = puppy.breed,
                    style = MaterialTheme.typography.body1,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = puppy.age,
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.LocationCity,
                        contentDescription = "location icon",
                        tint = MaterialTheme.colors.secondaryVariant,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = puppy.address,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}

@Preview("Puppy List", device = Devices.PIXEL_3)
@Composable
fun PuppyListPreview() {
    MyTheme {
        PuppyListContent(
            puppies = List(100) { FakePuppies.puppy1 },
            openDetails = {},
        )
    }
}
