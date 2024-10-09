package com.naol.moviemania.data.remote.model

import com.naol.moviemania.data.remote.model.Dates
import com.naol.moviemania.data.remote.model.ApiMovie

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<ApiMovie>,
    val total_pages: Int,
    val total_results: Int
)