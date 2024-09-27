package com.naol.moviemania.data.api.model

import com.naol.moviemania.data.model.Dates
import com.naol.moviemania.data.model.ApiMovie

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<ApiMovie>,
    val total_pages: Int,
    val total_results: Int
)