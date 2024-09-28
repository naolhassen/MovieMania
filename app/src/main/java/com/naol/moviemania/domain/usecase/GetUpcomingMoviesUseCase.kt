package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import com.naol.moviemania.presentation.home.MovieCatalog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUpcomingMoviesUseCase(private val repository: MovieManiaRepository) {
    fun execute(): Flow<NetworkResult<MovieResponse>> = flow {
        emit(repository.getMovies(MovieCatalog.UPCOMING.route))
    }
}