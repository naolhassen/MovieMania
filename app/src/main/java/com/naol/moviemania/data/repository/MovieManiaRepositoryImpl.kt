package com.naol.moviemania.data.repository

import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.model.NowPlayingResponse
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieManiaRepositoryImpl(private val api: TMDBApi) : MovieManiaRepository {
    override suspend fun getNowPlayingMovies(): NetworkResult<NowPlayingResponse> {
//        return flow {
//            val response = try {
//                api.getNowPlayingMovies()
//            } catch (e: Exception) {
//                emit(NetworkResult.Error(e.message ?: "Unknown error"))
//                return@flow
//            }
//            emit(NetworkResult.Success(response))
//        }

        return try {
            NetworkResult.Success(api.getNowPlayingMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}