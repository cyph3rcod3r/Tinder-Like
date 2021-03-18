package com.example.tinder.domain.source

import com.example.tinder.domain.AppDatabase
import com.example.tinder.domain.dao.PersonDao
import com.example.tinder.domain.model.Person
import javax.inject.Inject

open class LocalSource @Inject constructor(private val personDao: PersonDao) {
    suspend fun getAllPersons() = personDao.getAll()
    suspend fun savePerson(person: Person) = personDao.insert(person = person)
}