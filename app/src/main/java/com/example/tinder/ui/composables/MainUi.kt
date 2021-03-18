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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tinder.MainViewModel
import com.example.tinder.R
import com.example.tinder.composables.SwipeCard
import com.example.tinder.domain.states.TinderState
import com.example.tinder.domain.model.Person
import com.example.tinder.ui.TinderTheme
import com.example.tinder.util.SwipeResult

/**
 * Its the entry point for the jetpack compose UI.
 * It accepts the main view model and subscribes to the StateFlow for the new UI State
 * We keep it follow MVI architecture smoothly
 */
@ExperimentalMaterialApi
@Composable
fun MainUi(mainViewModel: MainViewModel, onSwiped: (result: SwipeResult, person: Person) -> Unit, onFavouriteClick:() -> Unit) {
    /**
     * Listen to state flow continuously and refresh the UI.
     */
    val tinderState by mainViewModel.state.collectAsState()
    when (tinderState) {
        /**
         * Error UI State
         */
        is TinderState.Error -> Toast.makeText(
            LocalContext.current,
            (tinderState as TinderState.Error).error,
            Toast.LENGTH_LONG
        ).show()
        /**
         * Extra state for loading Not required in this activity for us
         */
        TinderState.Loading -> TODO()
        /**
         * Success State. The Data has been received now time to prepare the UI
         */
        is TinderState.Success -> {
            if ((tinderState as TinderState.Success).persons.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(144.dp),
                    painter = painterResource(id = R.drawable.tinder),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onFavouriteClick() }) {
                    Text(text = "See Favourites ->")
                }
                Spacer(modifier = Modifier.height(32.dp))
                Box(modifier = Modifier.fillMaxSize()) {
                    (tinderState as TinderState.Success).persons.forEachIndexed { _, person ->
                        /**
                         * SwipeCard is a swipeable card all together which is a BOX by nature
                         * and supports touch events of drag
                         */
                        SwipeCard(
                            onSwiped = { onSwiped(it, person) }, modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.TopCenter)
                        ) {
                            /**
                             * Actual person card to hold person details
                             */
                            PersonCard(person = person)
                        }
                    }
                }
            }
        }
        /**
         * Extra state to handle throwables Probably send them to crashlytics?
         */
        is TinderState.Thr -> TODO()
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    TinderTheme {
//        App(MainViewModel(Repository(LocalSource(), RemoteSource()))) { _, _ -> }
    }
}