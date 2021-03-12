package com.moanes.datasource.repositories

import com.moanes.datasource.model.Character
import com.moanes.datasource.network.Service
import javax.inject.Inject

interface CharactersRepo {
    suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): List<Character>
}


class CharactersRepoImpl @Inject constructor(private val remoteService: Service) :
    CharactersRepo {
    override suspend fun getCharacters(offset: Int, limit: Int): List<Character> {
        return remoteService.getCharacters(offset, limit)
    }
}