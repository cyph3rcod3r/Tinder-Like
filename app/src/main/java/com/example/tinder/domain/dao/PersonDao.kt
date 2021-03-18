package com.example.tinder.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tinder.domain.model.Person

@Dao
abstract class  PersonDao {
    @Query("SELECT * FROM person")
    abstract suspend fun getAll(): List<Person>

    @Insert
    abstract suspend fun insert(person: Person): Long
}