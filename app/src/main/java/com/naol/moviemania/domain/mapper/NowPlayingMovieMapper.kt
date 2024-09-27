package com.naol.moviemania.domain.mapper

import com.naol.moviemania.data.model.NowPlaying
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.util.toDate

fun NowPlaying.toNowPlayingMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.poster_path,
        releaseDate = this.release_date.toDate(),
        voteAverage = this.vote_average,
        backdropPath = this.backdrop_path,
        popularity = this.popularity
    )
}
