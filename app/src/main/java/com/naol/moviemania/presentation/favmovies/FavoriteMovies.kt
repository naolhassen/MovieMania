package com.naol.moviemania.presentation.favmovies

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.remote.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.presentation.components.StarRating
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.SecondaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMoviesViewModel = koinViewModel()
) {
    val favMovies = viewModel.ldFavMovies.collectAsState().value
    LaunchedEffect(key1 = true, block = {
        viewModel.loadData()
    })

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favMovies) { movie ->
            SwipeableFavMovieItem(movie = movie, onRemove = { movieToRemove ->
                viewModel.removeFavMovie(movieToRemove)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableFavMovieItem(
    movie: Movie,
    onRemove: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState()
    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onRemove(movie)
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.errorContainer)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Remove from favorites"
                )
            }
        },
    ) {
        FavMovieItem(movie = movie)
    }
}

@Composable
fun FavMovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp)
            .background(SecondaryColor)
    ) {
        val imageURL = IMAGE_URL + movie.posterPath
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
                text = movie.releaseDate,
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
                rating = movie.voteAverage / 2,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}