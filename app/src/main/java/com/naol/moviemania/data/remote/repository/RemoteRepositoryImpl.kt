package com.naol.moviemania.data.remote.repository

import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.TMDBApi
import com.naol.moviemania.data.remote.model.MovieResponse
import com.naol.moviemania.data.remote.model.ApiMovieDetails
import com.naol.moviemania.data.remote.model.CastsResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository

class RemoteRepositoryImpl(private val api: TMDBApi) : MovieManiaRepository {
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

    override suspend fun getMovieCredits(movieId: Int): NetworkResult<CastsResponse> {
        return try {
            NetworkResult.Success(api.getMovieCredits(movieId))
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}