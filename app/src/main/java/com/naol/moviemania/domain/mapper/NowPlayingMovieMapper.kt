package com.naol.moviemania.domain.mapper

import com.naol.moviemania.domain.model.NowPlaying
import com.naol.moviemania.domain.util.toDate

class NowPlayingMovieMapper {
    fun map(nowPlayingMovie: com.naol.moviemania.data.model.NowPlaying): NowPlaying {
        return NowPlaying(
            id = nowPlayingMovie.id,
            title = nowPlayingMovie.title,
            overview = nowPlayingMovie.overview,
            posterPath = nowPlayingMovie.poster_path,
            releaseDate = nowPlayingMovie.release_date.toDate(),
            voteAverage = nowPlayingMovie.vote_average,
            backdropPath = nowPlayingMovie.backdrop_path,
            popularity = nowPlayingMovie.popularity
        )
    }
}