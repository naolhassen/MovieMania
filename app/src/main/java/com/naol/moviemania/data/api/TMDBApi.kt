package com.naol.moviemania.data.api

import com.naol.moviemania.data.api.model.NowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

//    @GET("movie/popular")
//    suspend fun getPopularMovies(): Response<PopularMoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingResponse>

}