package com.naol.moviemania.domain.mapper

import com.naol.moviemania.data.remote.model.ApiMovie
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.util.toDate

fun ApiMovie.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        releaseDate = release_date.toDate(),
        voteAverage = vote_average,
        popularity = popularity,
        backdropPath = backdrop_path ?: "",
    )
}


