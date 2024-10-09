package com.naol.moviemania.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.remote.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.DangerColor
import com.naol.moviemania.ui.theme.Green200
import com.naol.moviemania.ui.theme.NeutralColor
import com.naol.moviemania.ui.theme.SecondaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily

@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable { onClick() }
    )
    {
        val imagePath = IMAGE_URL + movie.posterPath
        AsyncImage(
            model = imagePath,
            contentDescription = ""
        )

        Box(
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .offset(y = (-20).dp, x = 8.dp)
        ) {
            RatingIndicator(rating = movie.voteAverage)

            Text(
                text = buildAnnotatedString {
                    append((movie.voteAverage * 10).toInt().toString())
                    withStyle(
                        style = SpanStyle(
                            baselineShift = BaselineShift.Superscript,
                            fontSize = 8.sp
                        ),
                    ) {
                        append("%")
                    }
                },
                color = NeutralColor,
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFontFamily,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 4.dp)
            )
        }

        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFontFamily,
            fontSize = 14.sp,
            modifier = Modifier
                .offset(y = (-20).dp)
                .padding(start = 16.dp)
        )
        Text(
            text = movie.releaseDate,
            fontWeight = FontWeight.Light,
            fontFamily = robotoFontFamily,
            fontSize = 12.sp,
            modifier = Modifier
                .offset(y = (-20).dp)
                .padding(start = 16.dp,bottom = 8.dp)
        )
    }
}


@Composable
fun RatingIndicator(rating: Double, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        drawArc(
            color = SecondaryColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = 3.dp.toPx())
        )
        val progressColor = if (rating >= 7) {
            Green200
        } else if (rating >= 5) {
            AccentColor
        } else {
            DangerColor
        }
        val ratingPercentage = 36 * rating

        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = ratingPercentage.toFloat(),
            useCenter = false,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

@Preview
@Composable
private fun MoviesPreview() {
    val movie = Movie(
        id = 1,
        title = "The Shawshank Redemption",
        posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        overview = "The story of a young man named Frank Red, who committed murder and attempted to exact revenge on his abusive parents.",
        releaseDate = "1994-09-10",
        voteAverage = 9.2,
        popularity = 936.055,
        backdropPath = "/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        MovieListItem(movie = movie, modifier = Modifier.align(Alignment.Center))
    }
}