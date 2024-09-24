package com.naol.moviemania.domain.repository

import com.naol.moviemania.data.api.model.NowPlayingResponse
import com.naol.moviemania.data.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieManiaRepository {
    suspend fun getNowPlayingMovies(): NetworkResult<NowPlayingResponse>

}