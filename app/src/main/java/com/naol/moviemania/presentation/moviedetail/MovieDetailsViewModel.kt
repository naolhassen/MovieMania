package com.naol.moviemania.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.model.ApiMovieDetails
import com.naol.moviemania.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch



sealed class UiState {
    object Loading : UiState()
    data class Success(val movieDetails: ApiMovieDetails) : UiState()
    data class Error(val message: String) : UiState()
}

class MovieDetailsViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {
    private var _ldMovieDetails = MutableStateFlow<UiState>(UiState.Loading)
    val ldMovieDetails = _ldMovieDetails

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
}