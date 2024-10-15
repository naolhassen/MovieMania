package com.naol.moviemania.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.domain.mapper.toMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.usecase.GetMoviesUseCase
import com.naol.moviemania.domain.usecase.IsFavMovieUseCase
import com.naol.moviemania.domain.usecase.RemoveFavMovieUseCase
import com.naol.moviemania.domain.usecase.SaveFavMovieUseCase
import com.naol.moviemania.presentation.home.MovieCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val saveFavMovieUseCase: SaveFavMovieUseCase,
    private val removeFavMovieUseCase: RemoveFavMovieUseCase,
    private val isFavMovieUseCase: IsFavMovieUseCase,
) :
    ViewModel() {
    private val _ldNowPlayingMovies =
        MutableStateFlow<NetworkResult<List<Movie>>>(NetworkResult.Loading)
    val ldNowPlayingMovies = _ldNowPlayingMovies.asStateFlow()


    fun toggleFavMovie(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                removeFavMovieUseCase.remove(movie)
            } else {
                saveFavMovieUseCase.save(movie)
            }
            // Update favorite status in the movie list after toggle
            _ldNowPlayingMovies.value.let { result ->
                if (result is NetworkResult.Success) {
                    val updatedMovies = result.data.map { m ->
                        if (m.id == movie.id) {
                            m.copy(isFavorite = !m.isFavorite) // Toggle favorite status in the movie list
                        } else {
                            m
                        }
                    }
                    // Emit the updated list wrapped in NetworkResult.Success
                    _ldNowPlayingMovies.emit(NetworkResult.Success(updatedMovies))
                }
            }
        }
    }

    fun fetchInitialData() {
        viewModelScope.launch {
            getMoviesUseCase.execute(MovieCatalog.NOW_PLAYING.route).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.results.let {
                            val movies = it.map { movies -> movies.toMovie() }
                                .map { movie ->
                                    movie.copy(
                                        isFavorite = isFavMovieUseCase.isFavMovie(movie.id)
                                    )
                                }
                            _ldNowPlayingMovies.emit(NetworkResult.Success(movies))
                        }
                    }

                    is NetworkResult.Error -> {
                        _ldNowPlayingMovies.emit(NetworkResult.Error(result.message))
                    }

                    NetworkResult.Loading -> {

                    }
                }
            }
        }
    }


}