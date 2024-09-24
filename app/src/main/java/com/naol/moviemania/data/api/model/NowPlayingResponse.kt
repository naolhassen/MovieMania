package com.naol.moviemania.data.api.model

import com.naol.moviemania.data.model.Dates
import com.naol.moviemania.data.model.NowPlaying

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlaying>,
    val total_pages: Int,
    val total_results: Int
)