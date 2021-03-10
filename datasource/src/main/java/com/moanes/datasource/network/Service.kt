package com.moanes.datasource.network

import com.moanes.datasource.model.Character
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Character>
}