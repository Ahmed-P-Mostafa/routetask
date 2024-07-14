package com.polotika.routetask.data.services

sealed class ApiResponse<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): ApiResponse<T>(data)
    object Loading : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
    data class Failure(val errorMessage: String) : ApiResponse<Nothing>()
}