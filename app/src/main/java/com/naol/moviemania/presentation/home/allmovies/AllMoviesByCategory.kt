package com.naol.moviemania.presentation.home.allmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.presentation.home.components.MovieListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllMoviesScreen(
    title: String,
    route: String,
    modifier: Modifier = Modifier,
    viewModel: AllMoviesViewModel = koinViewModel()
) {
    val viewState by viewModel.ldMovies.collectAsState()
    LaunchedEffect(key1 = viewModel, block = { viewModel.getInitialMovies(route) })

    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val successState = viewState as? NetworkResult.Success ?: return@derivedStateOf false
            val totalItemCount = successState.data.size
            val lastVisibleItemPosition = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
            lastVisibleItemPosition >= totalItemCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) {
            viewModel.loadMoreMovies(route)
        }
    })

    when (val state = viewState) {
        is NetworkResult.Success -> {
            LazyVerticalGrid(
                state = scrollState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
                modifier = modifier.padding(top = 80.dp)
            ) {
                items(state.data) { movie ->
                    MovieListItem(movie)
                }
            }

        }

        is NetworkResult.Error -> {
            Text(text = "Errorororor")
        }

        NetworkResult.Loading -> {
            Text(text = "Loading...")
        }
    }

}