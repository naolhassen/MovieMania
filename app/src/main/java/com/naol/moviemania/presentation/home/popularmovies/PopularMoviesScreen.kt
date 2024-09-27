package com.naol.moviemania.presentation.home.popularmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naol.moviemania.presentation.home.components.MovieListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: PopularMoviesViewModel = koinViewModel()
) {
    val movies = viewModel.ldPopularMovies.collectAsState().value
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp)
    ) {
        items(movies) { movie ->
            MovieListItem(movie)
        }
    }

}