package com.naol.moviemania.domain.usecase

import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.CastsResponse
import com.naol.moviemania.domain.repository.MovieManiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieCreditsUseCase(private val repository: MovieManiaRepository) {
    fun execute(movieId: Int): Flow<NetworkResult<CastsResponse>> = flow {
        emit(repository.getMovieCredits(movieId))
    }
}