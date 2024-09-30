package com.naol.moviemania.domain.repository

import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.data.NetworkResult

interface MovieManiaRepository {
    suspend fun getMovies(category: String, page: Int): NetworkResult<MovieResponse>

    suspend fun searchMovies(query: String, page: Int): NetworkResult<MovieResponse>
}