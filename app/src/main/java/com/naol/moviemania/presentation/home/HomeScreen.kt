package com.naol.moviemania.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingScreen
import com.naol.moviemania.presentation.home.popularmovies.PopularMoviesScreen
import com.naol.moviemania.presentation.home.topratedmovies.TopRatedMoviesScreen
import com.naol.moviemania.presentation.home.upcomingmovies.UpcomingMoviesScreen
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily



@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    NowPlayingScreen(modifier = modifier)
    LazyColumn(modifier = modifier) {
        item { NowPlayingScreen(modifier = modifier) }
        item { SectionTitle(title = "Popular Movies", modifier = Modifier.padding(16.dp)) }
        item { PopularMoviesScreen(modifier = modifier) }
        item { SectionTitle(title = "Top Rated Movies", modifier = Modifier.padding(16.dp)) }
        item { TopRatedMoviesScreen(modifier = modifier) }
        item { SectionTitle(title = "Upcoming Movies", modifier = Modifier.padding(16.dp)) }
        item { UpcomingMoviesScreen(modifier = modifier) }
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title, color = PrimaryColor,
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = modifier
    )
}





