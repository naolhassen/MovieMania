package com.naol.moviemania.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.Green200
import com.naol.moviemania.ui.theme.NeutralColor
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.SecondaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily

@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
            .background(Color.Transparent)

    ) {

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
                .padding(start = 16.dp)
        )
        Text(
            text = movie.releaseDate,
            fontWeight = FontWeight.Light,
            fontFamily = robotoFontFamily,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 16.dp)
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
            PrimaryColor
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
    RatingIndicator(5.385)
}