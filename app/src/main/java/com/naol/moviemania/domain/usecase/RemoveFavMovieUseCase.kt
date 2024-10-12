package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.local.repository.FavoriteMovieRepository
import com.naol.moviemania.domain.model.Movie

class RemoveFavMovieUseCase(private val favoriteMovieRepository: FavoriteMovieRepository) {
    suspend fun remove(movie: Movie) {
        favoriteMovieRepository.deleteMovie(movie)
    }
}