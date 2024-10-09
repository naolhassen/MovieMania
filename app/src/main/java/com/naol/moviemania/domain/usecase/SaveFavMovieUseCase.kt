package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.local.repository.LocalRepository
import com.naol.moviemania.domain.model.Movie

class SaveFavMovieUseCase(private val localRepository: LocalRepository) {
    suspend fun save(movie: Movie) {
        localRepository.insertMovie(movie)
    }
}