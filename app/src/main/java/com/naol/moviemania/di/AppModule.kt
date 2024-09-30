package com.naol.moviemania.di


import com.naol.moviemania.BuildConfig
import com.naol.moviemania.data.api.ApiKeyInterceptor
import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.TMDBApi.Companion.BASE_URL
import com.naol.moviemania.data.repository.MovieManiaRepositoryImpl
import com.naol.moviemania.domain.repository.MovieManiaRepository
import com.naol.moviemania.domain.usecase.GetMoviesUseCase
import com.naol.moviemania.domain.usecase.SearchMoviesUseCase
import com.naol.moviemania.presentation.home.allmovies.AllMoviesViewModel
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingViewModel
import com.naol.moviemania.presentation.home.popularmovies.PopularMoviesViewModel
import com.naol.moviemania.presentation.home.topratedmovies.TopRatedMoviesViewModel
import com.naol.moviemania.presentation.home.upcomingmovies.UpcomingMoviesViewModel
import com.naol.moviemania.presentation.searchmovie.SearchMoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val apiKeyInterceptor = ApiKeyInterceptor(BuildConfig.TMDB_API_KEY)

    single {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(get())
            .build()
            .create(TMDBApi::class.java)
    }

    single<MovieManiaRepository>(qualifier = named("remote")) {
        MovieManiaRepositoryImpl(get())
    }

    single {
        GetMoviesUseCase(get(named("remote")))
    }

    single {
        SearchMoviesUseCase(get(named("remote")))
    }

    viewModel {
        PopularMoviesViewModel(get())
    }
    viewModel {
        TopRatedMoviesViewModel(get())
    }
    viewModel {
        NowPlayingViewModel(get())
    }
    viewModel {
        UpcomingMoviesViewModel(get())
    }
    viewModel {
        AllMoviesViewModel(get())
    }

    viewModel {
        SearchMoviesViewModel(get())
    }
}


