package com.naol.moviemania

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naol.moviemania.data.MovieRepository
import com.naol.moviemania.data.MovieRepositoryImpl
import com.naol.moviemania.ui.theme.MovieManiaTheme
import com.naol.moviemania.ui.theme.PrimaryColor
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.KoinContext
import org.koin.java.KoinJavaComponent.getKoin

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
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val repository: MovieRepository = getKoin().get()
    MovieManiaTheme {
        Greeting("Android")
    }
}