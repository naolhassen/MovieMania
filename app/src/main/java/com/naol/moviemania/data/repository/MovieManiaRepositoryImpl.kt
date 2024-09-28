package com.naol.moviemania.data.repository

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.model.MovieResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository

class MovieManiaRepositoryImpl(private val api: TMDBApi) : MovieManiaRepository {
    override suspend fun getNowPlayingMovies(): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getNowPlayingMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getUpcomingMovies(): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getUpcomingMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getPopularMovies(): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getPopularMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTopRatedMovies(): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getTopRatedMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMoviesByCategory(
        category: String,
        page: Int
    ): NetworkResult<MovieResponse> {
        return try {
            NetworkResult.Success(api.getMoviesByCategory(category, page))
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}