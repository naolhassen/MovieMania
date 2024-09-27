package com.naol.moviemania.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.model.ApiMovie
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {
    private var _ldNowPlayingMovies = MutableStateFlow<List<ApiMovie>>(emptyList())
    val ldNowPlayingMovies = _ldNowPlayingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.execute().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldNowPlayingMovies.emit(it)
                        }
                    }

                    is NetworkResult.Error -> {

                    }

                    NetworkResult.Loading -> {

                    }
                }
            }
        }
    }
}
