package com.naol.moviemania.domain.usecase

import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesByCategoryUseCase(private val repository: MovieManiaRepository) {
    fun execute(category: String, page: Int): Flow<List<Movie>> = flow {
        repository.getMoviesByCategory(category, page)
    }
}