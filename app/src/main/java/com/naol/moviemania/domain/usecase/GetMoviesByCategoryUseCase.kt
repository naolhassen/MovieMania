package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesByCategoryUseCase(private val repository: MovieManiaRepository) {
    fun execute(category: String, page: Int): Flow<NetworkResult<MovieResponse>> = flow {
        emit(repository.getMovies(category, page))
    }
}