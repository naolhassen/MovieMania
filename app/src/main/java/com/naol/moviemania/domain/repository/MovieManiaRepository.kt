package com.naol.moviemania.domain.repository

import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.data.NetworkResult

interface MovieManiaRepository {
    suspend fun getNowPlayingMovies(): NetworkResult<MovieResponse>

    suspend fun getUpcomingMovies(): NetworkResult<MovieResponse>

    suspend fun getPopularMovies(): NetworkResult<MovieResponse>

    suspend fun getTopRatedMovies(): NetworkResult<MovieResponse>

    suspend fun getMoviesByCategory(category: String, page: Int): NetworkResult<MovieResponse>

}