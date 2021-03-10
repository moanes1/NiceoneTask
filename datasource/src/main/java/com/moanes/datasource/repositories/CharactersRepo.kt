package com.moanes.datasource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.moanes.datasource.NetworkClient
import com.moanes.datasource.model.BaseResponse
import com.moanes.datasource.model.Character
import com.moanes.datasource.network.Service
import kotlinx.coroutines.*
import retrofit2.http.Query

interface CharactersRepo {
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): LiveData<BaseResponse<List<Character>>>
}

class CharactersRepoImpl(private val networkClient: NetworkClient<Service>) : CharactersRepo {
    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): LiveData<BaseResponse<List<Character>>> = liveData(Dispatchers.Main) {
        try {
            emit(BaseResponse.Loading(true))
            val result = withContext(Dispatchers.IO) {
                networkClient.getRetrofitService(Service::class.java).getCharacters(offset, limit)
            }
            emit(BaseResponse.Success(result))
            emit(BaseResponse.Loading(false))
        } catch (e: Exception) {
            emit(BaseResponse.Loading(false))
            emit(BaseResponse.Error(e.localizedMessage))
        }
    }

}