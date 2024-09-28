package com.naol.moviemania.domain.mapper

import com.naol.moviemania.data.model.ApiMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.util.toDate

fun ApiMovie.toMovie(): Movie {
    return Movie(
        id = id?: 0,
        title = title,
        overview = overview ?: "No overview available",
        posterPath = poster_path,
        releaseDate = release_date.toDate(),
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        backdropPath = backdrop_path ?: ""
    )
}
