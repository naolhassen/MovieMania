package com.naol.moviemania.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingScreen
import com.naol.moviemania.ui.theme.PrimaryColor
import com.naol.moviemania.ui.theme.robotoFontFamily


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    NowPlayingScreen(modifier = modifier)
    Column(modifier = modifier) {
        NowPlayingScreen(modifier = modifier)
        SectionTitle(title = "Popular Movies", modifier = Modifier.padding(16.dp))
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





