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
package com.example.tinder.domain.states

import com.example.tinder.domain.model.Person


sealed class TinderState {
    object Loading : TinderState()
    data class Success(val persons: List<Person>) : TinderState()
    data class Error(val error: String) : TinderState()
    data class Thr(val error: Throwable?) : TinderState()
}

data class Person(val a: String){}
data class Student(val a: String) {}

