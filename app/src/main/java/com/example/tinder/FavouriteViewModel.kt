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
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    /**
     * Get a list of locally saved favourites profile
     */
    fun getProfiles() {
        viewModelScope.launch {
            val persons = repository.getFavouriteProfiles()
            if (persons.isEmpty()) {
                _state.value = TinderState.Error("No favourites found!")
            } else {
                _state.value = TinderState.Success(persons)
            }
        }
    }

    private val _state =
        MutableStateFlow<TinderState>(TinderState.Success(emptyList<Person>().toMutableList()))
    val state: StateFlow<TinderState>
        get() = _state
}