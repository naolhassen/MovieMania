package com.naol.moviemania.presentation.home.upcomingmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naol.moviemania.presentation.components.MovieListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpcomingMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingMoviesViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit = {}
) {
    val movies = viewModel.ldUpcomingMovies.collectAsState().value

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp)
    ) {
        items(movies) {
            MovieListItem(movie = it, onClick = { onMovieClick(it.id) })
        }
    }
}