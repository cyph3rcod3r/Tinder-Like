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
package com.example.tinder.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tinder.domain.model.Person
import com.example.tinder.ui.TinderTheme
import com.example.tinder.util.SwipeResult

/**
 * Swipeable card. Uses Modifier property of swiper
 * It provides a swipe result with an enum ACCEPT or REJECT
 */
@ExperimentalMaterialApi
@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    onSwiped: (result: SwipeResult) -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    val swiped = remember { mutableStateOf(false) }
    BoxWithConstraints(modifier = modifier) {
        val swipeState = rememberSwipeState(
            maxWidth = constraints.maxWidth.toFloat(),
            maxHeight = constraints.maxHeight.toFloat()
        )
        if (swiped.value.not()) {
            Box(
                modifier = Modifier
                    .swiper(
                        state = swipeState,
                        onDragAccepted = {
                            swiped.value = true
                            onSwiped(SwipeResult.ACCEPT)
                        },
                        onDragRejected = {
                            swiped.value = true
                            onSwiped(SwipeResult.REJECT)
                        }
                    ),
                content = content
            )
        }
    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewSwipeCard() {
    TinderTheme {
        SwipeCard(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center), onSwiped = { /*TODO*/ }) {
//            PersonCard(Person(
//                "",
//                "Elliot Alderson", "12-12-2012", "Embassy Village, Gurgaon, 67876546", "+91 12334"
//            ))
        }
    }
}