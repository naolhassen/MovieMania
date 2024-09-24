package com.naol.moviemania.presentation.ui.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.model.NowPlaying
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getNowPlayingMoviesUseCase.execute().collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data.results.let {
                        _ldNowPlayingMovies.emit(it)
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