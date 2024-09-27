package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val repository: MovieManiaRepository) {
    fun execute(): Flow<NetworkResult<MovieResponse>> = flow {
        emit(repository.getPopularMovies())
    }
}