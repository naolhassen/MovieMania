package com.naol.moviemania.data.repository

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.data.model.ApiMovieDetails
import com.naol.moviemania.domain.repository.MovieManiaRepository

class MovieManiaRepositoryImpl(private val api: TMDBApi) : MovieManiaRepository {
    override suspend fun getMovies(
        category: String,
        page: Int
    ): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getMovies(category, page))
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun searchMovies(query: String, page: Int): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.searchMovies(query, page))
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkResult<ApiMovieDetails> {
        return try {
            NetworkResult.Success(api.getMovieDetails(movieId))
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}