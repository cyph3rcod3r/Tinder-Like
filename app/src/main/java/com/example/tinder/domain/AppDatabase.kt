package com.example.tinder.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tinder.domain.dao.PersonDao
import com.example.tinder.domain.model.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}