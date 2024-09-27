package com.naol.moviemania.presentation.home.nowplaying

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naol.moviemania.data.api.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.data.model.NowPlaying
import com.naol.moviemania.domain.util.toDate
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.NeutralColor
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NowPlayingScreen(
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel(),
) {
    val movies = viewModel.ldNowPlayingMovies.collectAsState().value
    val pagerState = rememberPagerState(pageCount = {
        movies.size
    })
    HorizontalPager(state = pagerState) { page ->
        NowPlaying(nowPlaying = movies[page])
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NowPlaying(
    nowPlaying: NowPlaying, modifier: Modifier = Modifier
) {
    val imagePath = IMAGE_URL + nowPlaying.backdrop_path

    Box(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
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
                text = nowPlaying.release_date.toDate(),
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
                text = "%.1f".format(nowPlaying.vote_average),
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
