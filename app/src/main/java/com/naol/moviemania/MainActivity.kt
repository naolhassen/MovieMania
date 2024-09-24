package com.naol.moviemania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naol.moviemania.data.NetworkResult
import com.naol.moviemania.presentation.ui.nowplaying.NowPlayingViewModel
import com.naol.moviemania.ui.theme.MovieManiaTheme
import com.naol.moviemania.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieManiaTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PrimaryColor)
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    viewModel: NowPlayingViewModel = koinViewModel(),
    modifier: Modifier = Modifier
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