package com.example.androiddevchallenge.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
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
        }, content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(puppies) { puppy ->
                    PuppyListItem(puppy, openDetails)
                }
            }
        })
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
                Text(
                    text = puppy.name,
                    style = MaterialTheme.typography.h6,
                )
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
                        Icons.Filled.LocationCity,
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
