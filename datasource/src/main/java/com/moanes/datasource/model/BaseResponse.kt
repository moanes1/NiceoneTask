package com.moanes.datasource.model

sealed class BaseResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : BaseResponse<T>()
    data class Error(val error: String) : BaseResponse<Nothing>()
    data class Loading(val isLoading: Boolean) : BaseResponse<Nothing>()
}
