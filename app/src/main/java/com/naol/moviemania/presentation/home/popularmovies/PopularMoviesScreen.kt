package com.naol.moviemania.presentation.home.popularmovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.data.model.NowPlaying
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingViewModel
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    val movies = viewModel.ldNowPlayingMovies.collectAsState().value
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(start = 8.dp,bottom = 8.dp)
    ) {
        items(movies) { movie ->
            PopularMovie(movie)
        }
    }

}

@Composable
fun PopularMovie(
    movie: NowPlaying,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
    ) {

        val imagePath = IMAGE_URL + movie.poster_path
        AsyncImage(
            model = imagePath,
            contentDescription = ""
        )

        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp)
                .offset(y = (-20).dp, x = 8.dp)
                .clip(CircleShape)
                .background(AccentColor)
        )

        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFontFamily,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        Text(
            text = "1994-09-23",
            modifier = Modifier
                .padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
private fun PopularMoviesPreview() {

}