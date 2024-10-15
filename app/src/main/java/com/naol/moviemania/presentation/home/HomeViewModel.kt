package com.naol.moviemania.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.RemoveFavMovieUseCase
import com.naol.moviemania.domain.usecase.SaveFavMovieUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val saveFavMovieUseCase: SaveFavMovieUseCase,
    private val removeFavMovieUseCase: RemoveFavMovieUseCase
) : ViewModel() {
    fun toggleFavMovie(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                removeFavMovieUseCase.remove(movie)
            } else {
                saveFavMovieUseCase.save(movie)
            }
        }
    }
}

