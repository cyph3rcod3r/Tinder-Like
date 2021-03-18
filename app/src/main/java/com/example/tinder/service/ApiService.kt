package com.example.tinder.service

import com.example.tinder.domain.model.Base
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getUsers(@Query("results") results: Int): Base

}