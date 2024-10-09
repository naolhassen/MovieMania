package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.local.repository.FavoriteMovieRepository
import com.naol.moviemania.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavMoviesUseCase(private val localRepository: FavoriteMovieRepository) {
    fun execute(): Flow<List<Movie>> = flow {
        emit(localRepository.getAllMovies())
    }
}