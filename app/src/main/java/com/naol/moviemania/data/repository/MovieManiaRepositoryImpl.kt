package com.naol.moviemania.data.repository

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.model.NowPlayingResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository

class MovieManiaRepositoryImpl(private val api: TMDBApi) : MovieManiaRepository {
    override suspend fun getNowPlayingMovies(): NetworkResult<NowPlayingResponse> {
        return try {
            NetworkResult.Success(api.getNowPlayingMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}