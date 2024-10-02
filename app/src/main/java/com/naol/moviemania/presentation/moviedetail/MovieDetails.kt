package com.naol.moviemania.presentation.moviedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.naol.moviemania.ui.theme.PrimaryColor

@Composable
fun MovieDetailsScreen(id: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        ){
        Text(text = "MovieDetailsScreen $id", color = PrimaryColor, modifier = Modifier.align(Alignment.Center))

    }
}