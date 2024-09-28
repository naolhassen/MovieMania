package com.naol.moviemania.presentation.home

import androidx.lifecycle.ViewModel
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase

class HomeViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {

}
