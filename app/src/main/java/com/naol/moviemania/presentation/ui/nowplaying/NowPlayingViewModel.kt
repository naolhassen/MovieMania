package com.naol.moviemania.presentation.ui.nowplaying

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.model.NowPlayingResponse
import com.naol.moviemania.data.model.NowPlaying
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {
    private val _ldNowPlayingMovies =
        MutableStateFlow<List<NowPlaying>>(emptyList())
    val ldNowPlayingMovies = _ldNowPlayingMovies.asStateFlow()

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() = viewModelScope.launch {
        Log.d("TAG", "getNowPlayingMovies:")
        getNowPlayingMoviesUseCase.execute().collect { result ->
            Log.d("TAG", "getNowPlayingMovies:")
            when (result) {
                is NetworkResult.Success -> {
                    result.data.results.let {
                        _ldNowPlayingMovies.emit(it)
                        Log.d("TAG", "getNowPlayingMovies: ${it}")
                    }
                }

                is NetworkResult.Error -> {
                    _ldNowPlayingMovies.emit(emptyList())
                }

                NetworkResult.Loading -> {

                }
            }
        }
    }
}