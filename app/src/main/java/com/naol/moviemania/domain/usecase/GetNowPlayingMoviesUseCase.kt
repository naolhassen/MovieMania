package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.model.NowPlayingResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNowPlayingMoviesUseCase(private val repository: MovieManiaRepository) {
    fun execute(): Flow<NetworkResult<NowPlayingResponse>> = flow {
        emit(repository.getNowPlayingMovies())
    }
}