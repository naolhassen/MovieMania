package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.local.repository.FavoriteMovieRepository

class IsFavMovieUseCase(private val favoriteMovieRepository: FavoriteMovieRepository) {
    suspend fun isFavMovie(movieId: Int): Boolean {
        return favoriteMovieRepository.isMovieFavorite(movieId)
    }
}