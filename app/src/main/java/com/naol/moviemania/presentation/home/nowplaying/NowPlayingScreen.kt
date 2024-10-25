package com.naol.moviemania.presentation.home.nowplaying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.naol.moviemania.data.remote.model.NetworkResult
import com.naol.moviemania.data.remote.TMDBApi.Companion.IMAGE_URL
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.ui.theme.AccentColor
import com.naol.moviemania.ui.theme.NeutralColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue


@Composable
fun NowPlayingScreen(
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit = {}
) {
    val viewState by viewModel.ldNowPlayingMovies.collectAsState()
    LaunchedEffect(key1 = viewModel, block = { viewModel.fetchInitialData() })
    when (val state = viewState) {
        is NetworkResult.Success -> {
            val pagerState = rememberPagerState(pageCount = {
                state.data.size
            })
            HorizontalPager(
                state = pagerState,
//                pageSize = threePagesPerViewport,
//                beyondViewportPageCount = 2,
                pageSpacing = 16.dp,
                contentPadding = PaddingValues(8.dp),
            ) { page ->
                val pageOffset = pagerState.getOffsetDistanceInPages(page).absoluteValue

                NowPlaying(
                    nowPlaying = state.data[page],
//                    modifier = Modifier
//                        .height(250.dp * (1 - pageOffset))
//                        .fillMaxHeight()
//                        .graphicsLayer {
//                            val offset =
//                                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
//
//                            alpha = lerp(
//                                start = 0.5f,
//                                stop = 1f,
//                                fraction = 1f - offset.coerceIn(0f, 1f)
//                            )
//                        }
//                    ,
                    onMovieClick = { onMovieClick(state.data[page].id) },
                    onFavoriteClick = { viewModel.toggleFavMovie(state.data[page]) })
            }
        }

        is NetworkResult.Error -> {}
        NetworkResult.Loading -> {

        }
    }

}

private val threePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return ((availableSpace - 2 * pageSpacing) * 0.5f).toInt()
    }
}

@Composable
fun NowPlaying(
    nowPlaying: Movie,
    modifier: Modifier = Modifier,
    onMovieClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val imagePath = IMAGE_URL + nowPlaying.backdropPath

    Box(
        modifier = Modifier.offset(y = (-16).dp)
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
            imageVector = if (nowPlaying.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
            tint = if (nowPlaying.isFavorite) AccentColor else NeutralColor,
            contentDescription = "",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
                .size(24.dp)
                .clickable { onFavoriteClick() }
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
                .clickable { onMovieClick() }
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
