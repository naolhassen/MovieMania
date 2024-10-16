package com.naol.moviemania.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.ApiMovieDetails
import com.naol.moviemania.data.remote.model.CastsResponse
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.mapper.toMovieDetails
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.model.MovieDetails
import com.naol.moviemania.domain.usecase.GetMovieCreditsUseCase
import com.naol.moviemania.domain.usecase.GetMovieDetailsUseCase
import com.naol.moviemania.domain.usecase.IsFavMovieUseCase
import com.naol.moviemania.domain.usecase.RemoveFavMovieUseCase
import com.naol.moviemania.domain.usecase.SaveFavMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val saveFavMovieUseCase: SaveFavMovieUseCase,
    private val removeFavMovieUseCase: RemoveFavMovieUseCase,
    private val isFavMovieUseCase: IsFavMovieUseCase,
) :
    ViewModel() {
    private var _ldMovieDetails = MutableStateFlow<UiState<MovieDetails>>(UiState.Loading)
    val ldMovieDetails = _ldMovieDetails

    private var _ldMovieCredits = MutableStateFlow<UiState<CastsResponse>>(UiState.Loading)
    val ldMovieCredits = _ldMovieCredits.asStateFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCase.execute(movieId).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _ldMovieDetails.emit(
                        UiState.Success(
                            result.data.toMovieDetails().copy(
                                isFavorite = isFavMovieUseCase.isFavMovie(result.data.toMovieDetails().id)
                            )
                        )
                    )
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }
    }

    fun getMovieCredits(movieId: Int) = viewModelScope.launch {
        getMovieCreditsUseCase.execute(movieId).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _ldMovieCredits.emit(UiState.Success(result.data))
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }
    }

    fun toggleFavorite(movie: Movie) = viewModelScope.launch{
        if (movie.isFavorite) {
            removeFavMovieUseCase.remove(movie)
        } else {
            saveFavMovieUseCase.save(movie)
        }

        _ldMovieDetails.value.let { result ->
            if (result is UiState.Success) {
                val updatedMovie = result.data.copy(isFavorite = !result.data.isFavorite)
                _ldMovieDetails.emit(UiState.Success(updatedMovie))
            }
        }
    }
}