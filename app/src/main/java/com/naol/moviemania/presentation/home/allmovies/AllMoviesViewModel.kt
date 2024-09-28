package com.naol.moviemania.presentation.home.allmovies

import androidx.lifecycle.ViewModel
import com.naol.moviemania.domain.usecase.GetMoviesByCategoryUseCase


class AllMoviesViewModel(private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase) : ViewModel() {
}