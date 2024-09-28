package com.naol.moviemania.presentation.home.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    ViewModel() {

    private var _ldPopularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldPopularMovies = _ldPopularMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldPopularMovies.emit(it.map { movies -> movies.toMovie() } as List<Movie>)
                        }
                    }

                    is NetworkResult.Error -> {
                        // Handle error
                    }

                    is NetworkResult.Loading -> {}
                }
            }
        }
    }
}