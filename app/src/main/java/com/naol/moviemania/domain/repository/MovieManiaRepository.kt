package com.naol.moviemania.domain.repository

import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.model.ApiMovieDetails
import com.naol.moviemania.data.model.CastsResponse

interface MovieManiaRepository {
    suspend fun getMovies(category: String, page: Int): NetworkResult<MovieResponse>

    suspend fun searchMovies(query: String, page: Int): NetworkResult<MovieResponse>

    suspend fun getMovieDetails(movieId: Int): NetworkResult<ApiMovieDetails>

    suspend fun getMovieCredits(movieId: Int): NetworkResult<CastsResponse>

}