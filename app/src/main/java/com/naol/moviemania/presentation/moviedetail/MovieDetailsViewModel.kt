package com.naol.moviemania.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.ApiMovieDetails
import com.naol.moviemania.data.remote.model.CastsResponse
import com.naol.moviemania.domain.usecase.GetMovieCreditsUseCase
import com.naol.moviemania.domain.usecase.GetMovieDetailsUseCase
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
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase
) :
    ViewModel() {
    private var _ldMovieDetails = MutableStateFlow<UiState<ApiMovieDetails>>(UiState.Loading)
    val ldMovieDetails = _ldMovieDetails

    private var _ldMovieCredits = MutableStateFlow<UiState<CastsResponse>>(UiState.Loading)
    val ldMovieCredits = _ldMovieCredits.asStateFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCase.execute(movieId).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _ldMovieDetails.emit(UiState.Success(result.data))
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
}