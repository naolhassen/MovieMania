package com.naol.moviemania.presentation.home.upcomingmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpcomingMoviesViewModel(private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase) :
    ViewModel() {
    private var _ldUpcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldUpcomingMovies = _ldUpcomingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getUpcomingMoviesUseCase.execute().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldUpcomingMovies.emit(it.map { apiMovie -> apiMovie.toMovie() })
                        }
                    }

                    is NetworkResult.Error -> {
                        _ldUpcomingMovies.emit(emptyList())
                    }

                    is NetworkResult.Loading -> {}
                }
            }
        }
    }
}