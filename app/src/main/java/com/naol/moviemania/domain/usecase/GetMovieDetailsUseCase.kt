package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.ApiMovieDetails
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(private val repository: MovieManiaRepository) {
    fun execute(movieId: Int): Flow<NetworkResult<ApiMovieDetails>> = flow {
        emit(repository.getMovieDetails(movieId))
    }
}