package com.naol.moviemania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.data.model.bottomNavItems
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingViewModel
import com.naol.moviemania.presentation.home.HomeScreen
import com.naol.moviemania.ui.theme.MovieManiaTheme
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.SecondaryColor
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor),
        topBar = {
            TopAppBar(title = { Text("Movie Mania") })
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            if (navController.currentDestination?.route != item.route) {
                                selectedIndex = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = if (index == selectedIndex) PrimaryColor else Color.Unspecified,
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Navigation(navController, innerPadding)
    }
}

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController, startDestination = bottomNavItems[0].route) {
        bottomNavItems.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    "home" -> HomeScreen(modifier = Modifier.padding(innerPadding))
                    "search" -> SearchScreen()
                    "profile" -> ProfileScreen()
                    "favorite" -> FavoriteScreen()
                }
            }
        }
    }
}

@Composable
fun FavoriteScreen() {
    Text(
        text = "FavoriteScreen ",
        color = PrimaryColor
    )
}

@Composable
fun ProfileScreen() {
    Text(
        text = "ProfileScreen ",
        color = PrimaryColor
    )
}

@Composable
fun SearchScreen() {
    Text(
        text = "SearchScreen ",
        color = PrimaryColor
    )
}


@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    val movies = viewModel.ldNowPlayingMovies.collectAsState().value
    LazyColumn {
        items(movies.size) { index ->
            Text(
                text = "Hello ${movies[index].title}!",
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieManiaTheme {
        Greeting("Android")
    }
}