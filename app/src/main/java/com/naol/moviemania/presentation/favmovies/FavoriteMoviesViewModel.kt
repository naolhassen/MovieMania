package com.naol.moviemania.presentation.favmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetFavMoviesUseCase
import com.naol.moviemania.domain.usecase.RemoveFavMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val removeFavMovieUseCase: RemoveFavMovieUseCase
) : ViewModel() {
    private var _ldFavMovies = MutableStateFlow<List<Movie>>(emptyList())
    val ldFavMovies = _ldFavMovies.asStateFlow()

    fun loadData() = viewModelScope.launch {
        getFavMoviesUseCase.execute().collect {
            _ldFavMovies.emit(it)
        }
    }

    fun removeFavMovie(movie: Movie) = viewModelScope.launch {
        removeFavMovieUseCase.remove(movie)
        _ldFavMovies.value = _ldFavMovies.value.filter { it.id != movie.id }
    }
}