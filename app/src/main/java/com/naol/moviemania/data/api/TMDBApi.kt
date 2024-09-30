package com.naol.moviemania.data.api

import com.naol.moviemania.data.api.model.MovieResponse
import okhttp3.Interceptor
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
    }

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse
}

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .url(originalRequest.url.newBuilder().addQueryParameter("api_key", apiKey).build())
            .build()
        return chain.proceed(modifiedRequest)
    }
}