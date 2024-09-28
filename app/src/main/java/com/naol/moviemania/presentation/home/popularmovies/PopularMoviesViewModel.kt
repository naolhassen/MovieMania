package com.naol.moviemania.presentation.home.popularmovies

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

class PopularMoviesViewModel(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private var _ldPopularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldPopularMovies = _ldPopularMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesUseCase.execute(MovieCatalog.POPULAR.route).collect { result ->
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