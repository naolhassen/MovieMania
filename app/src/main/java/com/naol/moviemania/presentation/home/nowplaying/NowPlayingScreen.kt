package com.naol.moviemania.presentation.home.nowplaying

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.NeutralColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel


@Composable
fun NowPlayingScreen(
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel(),
) {
    val movies = viewModel.ldNowPlayingMovies.collectAsState().value
    val pagerState = rememberPagerState(pageCount = {
        movies.size
    })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
    ) { page ->
        NowPlaying(nowPlaying = movies[page])
    }
}

@Composable
fun NowPlaying(
    nowPlaying: Movie, modifier: Modifier = Modifier
) {
    val imagePath = IMAGE_URL + nowPlaying.backdropPath

    Box(
        modifier = Modifier
            .offset(x = (-16).dp)
    ) {
        AsyncImage(
            model = imagePath,
            contentDescription = nowPlaying.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .padding(4.dp)
        )

        Icon(
            imageVector = Icons.Default.Favorite,
            tint = AccentColor,
            contentDescription = "",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
                .size(24.dp)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = nowPlaying.title,
                color = NeutralColor,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
            Text(
                text = nowPlaying.releaseDate,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "%.1f".format(nowPlaying.voteAverage),
                color = NeutralColor,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.Star,
                tint = AccentColor,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(16.dp)
            )
        }
    }
}
