package com.moanes.datasource.repositories

import com.moanes.datasource.NetworkClient
import com.moanes.datasource.model.Character
import com.moanes.datasource.network.Service
import retrofit2.http.Query

interface CharactersRepo {
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Character>
}

class CharactersRepoImpl(private val networkClient: NetworkClient<Service>) : CharactersRepo {
    override suspend fun getCharacters(offset: Int, limit: Int): List<Character> {
        return networkClient.getRetrofitService(Service::class.java).getCharacters(offset, limit)
    }
}