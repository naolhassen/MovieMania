package com.naol.moviemania.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {
    private val _ldNowPlayingMovies =
        MutableStateFlow<NetworkResult<List<Movie>>>(NetworkResult.Loading)
    val ldNowPlayingMovies = _ldNowPlayingMovies.asStateFlow()


    fun fetchInitialData() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.execute().collect { result ->

                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            val movies = it.map { movies -> movies.toMovie() }
                            _ldNowPlayingMovies.emit(NetworkResult.Success(movies))
                        }
                    }

                    is NetworkResult.Error -> {
                        _ldNowPlayingMovies.emit(NetworkResult.Error(result.message))
                    }

                    NetworkResult.Loading -> {

                    }
                }
            }
        }
    }
}