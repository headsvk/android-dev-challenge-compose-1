package com.example.androiddevchallenge.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
        })
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
