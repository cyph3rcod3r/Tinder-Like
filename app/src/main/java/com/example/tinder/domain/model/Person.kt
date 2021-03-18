package com.example.tinder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(@PrimaryKey(autoGenerate = true) val id: Int = 0, val img: String? = null, val name: String? = null, val dob: String? = null, val address: String? = null, val phone: String? = null)
