package com.naol.moviemania.presentation.home.allmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetMoviesByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AllMoviesViewModel(private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase) :
    ViewModel() {
    private var _ldMovies = MutableStateFlow<NetworkResult<List<Movie>>>(NetworkResult.Loading)
    val ldMovies = _ldMovies.asStateFlow()

    private val fetchedMovies = mutableListOf<List<Movie>>()

    fun getInitialMovies(category: String) = viewModelScope.launch {
        getMoviesByCategoryUseCase.execute(category, 1).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data.results.let {
                        fetchedMovies.clear()
                        val movies = it.map { movies -> movies.toMovie() }
                        fetchedMovies.add(movies)
                        _ldMovies.emit(NetworkResult.Success(movies))
                    }
                }

                is NetworkResult.Error -> {

                }

                NetworkResult.Loading -> {

                }
            }
        }
    }

    fun loadMoreMovies(category: String) = viewModelScope.launch {
        val nextPage = fetchedMovies.size + 1
        getMoviesByCategoryUseCase.execute(category, nextPage).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data.results.let { response ->
                        val movies = response.map { movie -> movie.toMovie() }
                            ?: emptyList()
                        fetchedMovies.add(movies)
                        _ldMovies.update { NetworkResult.Success(fetchedMovies.flatten()) }
                    }
                }

                is NetworkResult.Error -> {}
                NetworkResult.Loading -> {}
            }
        }
    }

}