package com.naol.moviemania.domain.model

sealed class NetworkResult {
    data class Success<T>(val data: T) : NetworkResult()
    data class Error(val message: String) : NetworkResult()
    data object Loading : NetworkResult()
}