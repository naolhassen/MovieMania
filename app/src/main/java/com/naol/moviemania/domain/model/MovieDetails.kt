package com.naol.moviemania.domain.model

import com.naol.moviemania.data.remote.model.Genre

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val backdropPath: String,
    val isFavorite: Boolean = false,
    val tagline: String,
    val genres: List<Genre>,
)