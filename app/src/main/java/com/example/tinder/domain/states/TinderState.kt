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

