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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CalendarToday
import androidx.compose.material.icons.twotone.MyLocation
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material.icons.twotone.SupervisedUserCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.example.tinder.domain.model.Person
import com.example.tinder.ui.TinderTheme
import com.example.tinder.ui.grey200
import com.example.tinder.ui.grey400
import com.example.tinder.ui.purple500
import com.example.tinder.util.getSecuredUrl
import com.example.tinder.util.limited
import dev.chrisbanes.accompanist.coil.CoilImage
import java.net.URL

/**
 * Composable to create a person card 1 box
 */
@Composable
fun PersonCard(person: Person) {
    var details by remember { mutableStateOf(person.name) }
    val user = mutableStateOf(true)
    val dob = mutableStateOf(false)
    val loc = mutableStateOf(false)
    val phone = mutableStateOf(false)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = RoundedCornerShape(4.dp), color = grey200) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                /*Image(
                    painter = painterResource(id = R.drawable.random),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(196.dp),
                    contentDescription = null
                )*/
                CoilImage(
                    data = URL(person.img.orEmpty()).getSecuredUrl(),
                    contentDescription = "My content description",
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    }, modifier = Modifier.size(192.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = details.orEmpty().limited(), overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    IconButton(onClick = {
                        user.value = true; dob.value = false; loc.value = false;phone.value = false
                        details = person.name.orEmpty()
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.SupervisedUserCircle,
                            tint = if (user.value) purple500 else grey400,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp)
                        )
                    }
                    IconButton(onClick = {
                        dob.value = true; user.value = false; loc.value = false;phone.value = false
                        details = person.dob
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.CalendarToday,
                            tint = if (dob.value) purple500 else grey400,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp)
                        )
                    }
                    IconButton(onClick = {
                        loc.value = true; dob.value = false; user.value = false;phone.value = false
                        details = person.address
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.MyLocation,
                            tint = if (loc.value) purple500 else grey400,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp)
                        )
                    }
                    IconButton(onClick = {
                        phone.value = true; dob.value = false; loc.value = false;user.value = false
                        details = person.phone
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Phone,
                            tint = if (phone.value) purple500 else grey400,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    TinderTheme {
        PersonCard(
            Person(id = 0,
                "Elliot Alderson", "12-12-2012", "Embassy Village, Gurgaon, adiuasiduiudhiasduhaisdhiasd", "+91 12334"
            )
        )
    }
}