package com.naol.moviemania.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingScreen
import com.naol.moviemania.presentation.home.popularmovies.PopularMoviesScreen
import com.naol.moviemania.presentation.home.topratedmovies.TopRatedMoviesScreen
import com.naol.moviemania.presentation.home.upcomingmovies.UpcomingMoviesScreen
import com.naol.moviemania.ui.theme.LightColor2
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailScreen(
    val id: Int
)

@Serializable
data class MovieByCategoryRoute(
    val title: String,
    val route: String
)

enum class MovieCategory(val title: String, val route: String) {
    POPULAR("Popular", "/popular"),
    TOP_RATED("Top Rated", "/top_rated"),
    UPCOMING("Upcoming", "/upcoming")
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item { NowPlayingScreen(modifier = modifier) }
        for (category in MovieCategory.entries) {
            item {
                SectionTitle(category,
                    onClick = {
                        navController.navigate(MovieByCategoryRoute(category.title, category.route))
                    })
            }
            when (category) {
                MovieCategory.POPULAR -> item { PopularMoviesScreen() }
                MovieCategory.TOP_RATED -> item { TopRatedMoviesScreen() }
                MovieCategory.UPCOMING -> item { UpcomingMoviesScreen() }
            }
        }
    }
}

@Composable
fun SectionTitle(category: MovieCategory, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = category.title, color = PrimaryColor,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            text = "SEE ALL",
            color = LightColor2,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            modifier = Modifier.clickable {
                onClick()
            }
        )
    }
}





