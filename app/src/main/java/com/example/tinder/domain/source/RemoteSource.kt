package com.example.tinder.domain.source

import com.example.tinder.service.ApiService
import javax.inject.Inject

open class RemoteSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getProfiles() = apiService.getUsers(20)
}