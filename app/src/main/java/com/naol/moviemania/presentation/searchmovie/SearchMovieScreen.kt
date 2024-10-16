package com.naol.moviemania.presentation.searchmovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.naol.moviemania.R
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.data.remote.model.ApiMovie
import com.naol.moviemania.presentation.components.StarRating
import com.naol.moviemania.presentation.home.MovieDetailScreenRoute
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesScreen(
    navController: NavController,
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
        if (query.isNotEmpty() && searchResults is NetworkResult.Success) {
            viewModel.loadMoreResults(query)
        }
    })

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
        ) {
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
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (active) {
                        IconButton(onClick = {
                            if (query.isNotBlank()) {
                                query = ""
                            } else {
                                active = false
                            }
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear Search")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp)

            ) {

            }
        }
        when (val state = searchResults) {
            is NetworkResult.Success -> {
                LazyColumn(
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.data) { movie ->
                        SearchResultItem(movie = movie, onMovieClick = {
                            navController.navigate(
                                MovieDetailScreenRoute(it)
                            )
                        })
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
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        val imageURL = IMAGE_URL + movie.poster_path
        AsyncImage(
            model = imageURL,
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
                .padding(end = 8.dp)
        ) {
            Text(
                text = movie.title,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = AccentColor,
            )
            Text(
                text = movie.release_date,
                fontWeight = FontWeight.Normal,
                fontFamily = robotoFontFamily
            )
            Text(
                text = movie.overview,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                fontFamily = robotoFontFamily,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            StarRating(
                rating = movie.vote_average / 2,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

