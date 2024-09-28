package com.naol.moviemania.presentation.home.upcomingmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetMoviesUseCase
import com.naol.moviemania.presentation.home.MovieCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpcomingMoviesViewModel(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {
    private var _ldUpcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldUpcomingMovies = _ldUpcomingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesUseCase.execute(MovieCatalog.UPCOMING.route).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldUpcomingMovies.emit(it.map { apiMovie -> apiMovie.toMovie() } as List<Movie>)
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