package com.naol.moviemania.di


import com.naol.moviemania.BuildConfig
import com.naol.moviemania.data.api.ApiKeyInterceptor
import com.naol.moviemania.data.api.TMDBApi
import com.naol.moviemania.data.api.TMDBApi.Companion.BASE_URL
import com.naol.moviemania.data.repository.MovieManiaRepositoryImpl
import com.naol.moviemania.domain.repository.MovieManiaRepository
import com.naol.moviemania.domain.usecase.GetNowPlayingMoviesUseCase
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingViewModel
import com.naol.moviemania.presentation.home.HomeViewModel
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
        GetNowPlayingMoviesUseCase(get(named("remote")))
    }

    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        NowPlayingViewModel(get())
    }
}


