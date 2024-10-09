package com.naol.moviemania.presentation.searchmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.model.ApiMovie
import com.naol.moviemania.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchMoviesViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {
    private var _searchResults = MutableStateFlow<NetworkResult<List<ApiMovie>>>(NetworkResult.Loading)
    val searchResults = _searchResults.asStateFlow()

    private val fetchedResults = mutableListOf<List<ApiMovie>>()

    fun searchMovies(query: String) = viewModelScope.launch {
        searchMoviesUseCase.execute(query).collect { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val movies = response.data.results
                    fetchedResults.clear()
                    fetchedResults.add(movies)
                    _searchResults.emit(NetworkResult.Success(movies))
                }

                is NetworkResult.Error -> {
                    _searchResults.value = NetworkResult.Error(response.message)
                }

                is NetworkResult.Loading -> {
                    _searchResults.value = NetworkResult.Loading
                }
            }
        }
    }

    fun loadMoreResults(query: String) = viewModelScope.launch {
        val page = fetchedResults.size + 1
        searchMoviesUseCase.execute(query, page).collect { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val movies = response.data.results
                    fetchedResults.add(movies)
                    _searchResults.update { NetworkResult.Success(fetchedResults.flatten()) }
                }

                is NetworkResult.Error -> {
                    _searchResults.value = NetworkResult.Error(response.message)
                }

                is NetworkResult.Loading -> {
                    _searchResults.value = NetworkResult.Loading
                }
            }
        }
    }
}