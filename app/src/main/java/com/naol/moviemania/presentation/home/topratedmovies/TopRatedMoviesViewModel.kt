package com.naol.moviemania.presentation.home.topratedmovies

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

class TopRatedMoviesViewModel(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {
    private var _ldTopRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldTopRatedMovies = _ldTopRatedMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesUseCase.execute(MovieCatalog.TOP_RATED.route).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            _ldTopRatedMovies.emit(it.map { apiMovie -> apiMovie.toMovie() } as List<Movie>)
                        }
                    }

                    is NetworkResult.Error -> {
                        _ldTopRatedMovies.emit(emptyList())
                    }

                    is NetworkResult.Loading -> {}
                }
            }
        }
    }

}