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
package com.example.tinder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinder.domain.Repository
import com.example.tinder.domain.states.TinderState
import com.example.tinder.domain.model.Person
import com.example.tinder.util.NetworkHelper
import com.example.tinder.util.SwipeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var update: Long = 0L
    fun executeNext(swipeResult: SwipeResult, person: Person) {
        when (swipeResult) {
            SwipeResult.ACCEPT -> {
                saveUserProfile(person)
            }
            SwipeResult.REJECT -> {
            } /*Do Nothing*/
        }
    }

    fun getProfiles() {
        viewModelScope.launch {
            try {
                val base = repository.getTinderProfiles()
                _state.value = TinderState.Success(base.getPersons())
            } catch (e: Exception) {
                _state.value = TinderState.Thr(e.cause)
            }
        }
    }

    fun saveUserProfile(person: Person) {
        viewModelScope.launch {
            update = repository.savePersonProfile(person = person)
        }
    }

    private val _state =
        MutableStateFlow<TinderState>(TinderState.Success(emptyList<Person>().toMutableList()))
    val state: StateFlow<TinderState>
        get() = _state
}