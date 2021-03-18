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
package com.example.tinder.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.example.tinder.FavouriteViewModel
import com.example.tinder.domain.model.Person
import com.example.tinder.domain.states.TinderState
import com.example.tinder.util.getSecuredUrl
import dev.chrisbanes.accompanist.coil.CoilImage
import java.net.URL

/**
 * Loads a list of favourite person in a recyclerview
 */
@Composable
fun FavouritesUi(favouriteViewModel: FavouriteViewModel){
    val state by favouriteViewModel.state.collectAsState()
    when(state){
        is TinderState.Error -> Toast.makeText(LocalContext.current, (state as TinderState.Error).error, Toast.LENGTH_SHORT).show()
        TinderState.Loading -> TODO()
        is TinderState.Success -> {
            LazyColumn{
                items((state as TinderState.Success).persons){ person ->
                    ListItem(person = person)
                }
            }
        }
        is TinderState.Thr -> TODO()
    }
}

/**
 * a composable to create each row item
 */
@Composable
fun ListItem(person: Person){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
        CoilImage(
            data = URL(person.img.orEmpty()).getSecuredUrl(),
            contentDescription = "My content description",
            requestBuilder = {
                transformations(CircleCropTransformation())
            }, modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = person.name.orEmpty(), style = MaterialTheme.typography.h6, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Text(text = person.dob.orEmpty())
        }
    }
}