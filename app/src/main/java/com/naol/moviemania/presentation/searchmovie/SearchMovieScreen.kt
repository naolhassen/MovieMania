package com.naol.moviemania.presentation.searchmovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.data.model.ApiMovie
import com.naol.moviemania.domain.util.toDate
import com.naol.moviemania.presentation.home.components.RatingIndicator
import com.naol.moviemania.ui.theme.AccentColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMoviesViewModel = koinViewModel()
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState()

    val scrollState = rememberLazyListState()

    val loadMoreResults by remember {
        derivedStateOf {
            val successState =
                searchResults as? NetworkResult.Success ?: return@derivedStateOf false
            val totalItemCount = successState.data.size
            val lastVisibleItemPosition =
                scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: return@derivedStateOf false
            lastVisibleItemPosition >= totalItemCount - 10
        }
    }

    LaunchedEffect(key1 = loadMoreResults, block = {
        if (query.isNotEmpty()) {
            viewModel.loadMoreResults(query)
        }
    })

    Column(modifier = Modifier.padding(top = 56.dp)) {
        SearchBar(query = query, onQueryChange = {
            query = it
        }, onSearch = {
            viewModel.searchMovies(query)
            active = false
        }, active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                if (active) {
                    IconButton(onClick = {
                        if (query.isNotBlank()) {
                            query = ""
                        } else {
                            active = false
                        }
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = null)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

        }
        when (val state = searchResults) {
            is NetworkResult.Success -> {
                LazyColumn(
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.data) { movie ->
                        SearchResultItem(movie = movie)
                    }
                }
            }

            is NetworkResult.Error -> {}
            NetworkResult.Loading -> {}
        }
    }
}

@Composable
fun SearchResultItem(
    movie: ApiMovie,  // Replace with actual Movie model
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp)
            .clickable { /* Handle click event */ }

    ) {
        val imageURL = IMAGE_URL + movie.poster_path
        AsyncImage(
            model = imageURL,
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(100.dp)
                .padding(8.dp)
        )
        Column(modifier = modifier.align(Alignment.CenterVertically)) {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = movie.release_date)
            Text(
                text = movie.overview,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                maxLines = 2,
            )
            StarRating(rating = movie.vote_average,
                modifier = Modifier.align(Alignment.End))

        }

    }

}

@Composable
fun StarRating(rating: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (index+1 < rating) AccentColor else Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }

}
