package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.MovieResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCase(private val repository: MovieManiaRepository) {
    fun execute(catalog: String, page: Int = 1): Flow<NetworkResult<MovieResponse>> = flow {
        emit(repository.getMovies(catalog,page))
    }
}