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
package com.example.tinder.domain.source

import com.example.tinder.domain.AppDatabase
import com.example.tinder.domain.dao.PersonDao
import com.example.tinder.domain.model.Person
import javax.inject.Inject

open class LocalSource @Inject constructor(private val personDao: PersonDao) {
    suspend fun getAllPersons() = personDao.getAll()
    suspend fun savePerson(person: Person) = personDao.insert(person = person)
}