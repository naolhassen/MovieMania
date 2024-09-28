package com.naol.moviemania.presentation.home.allmovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.naol.moviemania.presentation.home.MovieCategory
import com.naol.moviemania.ui.theme.PrimaryColor

@Composable
fun AllMoviesScreen(title: String,route: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "$title Screen ", color = PrimaryColor,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}