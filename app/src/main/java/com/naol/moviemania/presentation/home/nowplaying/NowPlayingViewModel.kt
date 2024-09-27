package com.naol.moviemania.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toNowPlayingMovie
import com.naol.moviemania.domain.model.NowPlayingMovie
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {
    private val _ldNowPlayingMovies =
        MutableStateFlow<List<NowPlayingMovie>>(emptyList())
    val ldNowPlayingMovies = _ldNowPlayingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.execute().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldNowPlayingMovies.emit(it.map { movies -> movies.toNowPlayingMovie() })
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
}