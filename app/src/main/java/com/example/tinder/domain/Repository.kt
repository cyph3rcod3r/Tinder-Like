package com.example.tinder.domain

import com.example.tinder.domain.model.Person
import com.example.tinder.domain.source.LocalSource
import com.example.tinder.domain.source.RemoteSource
import javax.inject.Inject

open class Repository @Inject constructor(private val localSource: LocalSource, private val remoteSource: RemoteSource) {
    suspend fun getTinderProfiles() = remoteSource.getProfiles()
    suspend fun savePersonProfile(person: Person) = localSource.savePerson(person = person)
    suspend fun getFavouriteProfiles() = localSource.getAllPersons()
}