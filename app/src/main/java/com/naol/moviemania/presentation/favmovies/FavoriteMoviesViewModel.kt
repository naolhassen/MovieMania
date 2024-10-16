package com.naol.moviemania.presentation.favmovies

import androidx.lifecycle.ViewModel
import com.naol.moviemania.domain.usecase.GetFavMoviesUseCase

class FavoriteMoviesViewModel(private val getFavMoviesUseCase: GetFavMoviesUseCase) : ViewModel() {

}