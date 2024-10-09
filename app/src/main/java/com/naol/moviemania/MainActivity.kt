package com.naol.moviemania

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.naol.moviemania.data.remote.model.bottomNavItems
import com.naol.moviemania.presentation.home.HomeScreen
import com.naol.moviemania.presentation.home.MovieCategoryRoute
import com.naol.moviemania.presentation.home.MovieDetailScreenRoute
import com.naol.moviemania.presentation.home.allmovies.AllMoviesScreen
import com.naol.moviemania.presentation.moviedetail.MovieDetailsScreen
import com.naol.moviemania.presentation.searchmovie.SearchMoviesScreen
import com.naol.moviemania.ui.theme.LightColor
import com.naol.moviemania.ui.theme.MovieManiaTheme
import com.naol.moviemania.ui.theme.Pink
import com.naol.moviemania.ui.theme.Pink41
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.SecondaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieManiaTheme {
                MainScreen()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(modifier = Modifier.fillMaxSize(),
//        topBar = {
//            TopAppBar(title = { AppName() })
//        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(selected = index == selectedIndex, onClick = {
                        navigateToDestination(navController, item.route)
                        selectedIndex = index
                    }, icon = {
                        Icon(
                            imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = if (index == selectedIndex) PrimaryColor else LightColor,
                        )
                    }, label = {
                        Text(
                            text = item.title,
                            color = if (index == selectedIndex) PrimaryColor else LightColor
                        )
                    }
                    )
                }
            }
        }) { innerPadding ->
        Navigation(
            navController,
            Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
        )
    }
}


private fun navigateToDestination(
    navController: NavHostController,
    route: String
) {
    if (navController.currentDestination?.route != route) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
            restoreState = true
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController,
        startDestination = bottomNavItems[0].route,
        modifier = modifier
    ) {
        bottomNavItems.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    "home" -> HomeScreen(
                        navController = navController
                    )

                    "search" -> SearchMoviesScreen(navController = navController)
                    "favorite" -> FavoriteScreen("")
                    "profile" -> ProfileScreen()
                }
            }

            composable<MovieCategoryRoute> {
                val args = it.toRoute<MovieCategoryRoute>()
                AllMoviesScreen(args.title, args.route, navController)
            }

            composable<MovieDetailScreenRoute> {
                val args = it.toRoute<MovieDetailScreenRoute>()
                MovieDetailsScreen(args.id)
            }
        }
    }
}

@Composable
fun FavoriteScreen(category: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "$category Screen ", color = PrimaryColor,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ProfileScreen() {
    Text(
        text = "ProfileScreen ", color = PrimaryColor
    )
}


@Composable
fun AppName(
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Pink41, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
            ) {
                append("M")
            }
//            withStyle(style = SpanStyle(color = Pink)) {
//                append("ovie")
//            }
            withStyle(
                style = SpanStyle(
                    color = Pink, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
            ) {
                append("M")
            }
//            withStyle(style = SpanStyle(color = Pink81)) {
//                append("ania")
//            }
        },
        color = SecondaryColor,
        fontFamily = robotoFontFamily,
        modifier = modifier.background(Color.Transparent),
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieManiaTheme {
        AppName()
    }
}