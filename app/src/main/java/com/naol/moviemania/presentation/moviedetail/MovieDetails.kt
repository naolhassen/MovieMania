package com.naol.moviemania.presentation.moviedetail

import android.provider.MediaStore.Audio.Genres
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.data.model.Genre
import com.naol.moviemania.presentation.components.RatingIndicator
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    id: Int, modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val uiState = viewModel.ldMovieDetails.collectAsState().value
    LaunchedEffect(key1 = true, block = {
        viewModel.getMovieDetails(id)
    })

    when (uiState) {
        is UiState.Success -> {
            val movie = uiState.movieDetails
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Poster(movie.poster_path, movie.backdrop_path)
                Header(movie.title, movie.release_date.substring(0, 4))
                SecondaryHeader(movie.vote_average)
                Genres(movie.genres, modifier = Modifier.align(Alignment.CenterHorizontally))
                Overview(movie.overview, movie.tagline)
            }
        }

        is UiState.Error -> {}
        UiState.Loading -> {
            CircularProgressIndicator()
        }
    }

}


@Composable
fun Poster(
    posterPath: String,
    backdropPath: String,
    modifier: Modifier = Modifier
) {

    Box {
        val posterUrl = IMAGE_URL + posterPath
        val backdropUrl = IMAGE_URL + backdropPath
        AsyncImage(
            model = backdropUrl,
            contentDescription = "Movie backdrop",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        AsyncImage(
            model = posterUrl,
            contentDescription = "Movie poster",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .align(Alignment.TopStart)
                .padding(8.dp)
        )
    }

}

@Composable
fun Header(title: String, releaseYear: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            append(title)
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Light,

            )) {
                append(" (")
                append(releaseYear)
                append(")")
            }
        },
        color = PrimaryColor,
        fontSize = 24.sp,
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SecondaryHeader(rating: Double, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box {
            RatingIndicator(
                rating = rating,
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append((rating * 10).toInt().toString())
                    withStyle(
                        style = SpanStyle(
                            baselineShift = BaselineShift.Superscript,
                            fontSize = 8.sp
                        )
                    ) {
                        append("%")
                    }
                },
                color = PrimaryColor,
                fontSize = 20.sp,
                fontFamily = robotoFontFamily,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 4.dp)
            )
        }
        Text(
            text = "User Score",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFontFamily,
            modifier = Modifier.padding(end = 16.dp)
        )

        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(IntrinsicSize.Min)
                .background(PrimaryColor)
                .padding(vertical = 8.dp)
                .padding(horizontal = 28.dp)
        )

        Text(
            text = "What's your Vibe?",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFontFamily,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Genres(genres: List<Genre>, modifier: Modifier = Modifier) {
    Text(
        text = genres.joinToString(" | ") { it.name },
        color = PrimaryColor,
        fontSize = 14.sp,
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(top = 8.dp, start = 16.dp)
    )
}

@Composable
fun Overview(overview: String, tagline: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = tagline,
            color = PrimaryColor,
            fontSize = 16.sp,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Light,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Overview",
            color = PrimaryColor,
            fontSize = 16.sp,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Text(
            text = overview,
            color = PrimaryColor,
            fontSize = 14.sp,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}