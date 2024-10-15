package com.naol.moviemania.di


import androidx.room.Room
import com.naol.moviemania.BuildConfig
import com.naol.moviemania.data.local.MovieManiaDatabase
import com.naol.moviemania.data.local.MovieManiaDatabase.Companion.DATABASE_NAME
import com.naol.moviemania.data.local.dao.MovieDao
import com.naol.moviemania.data.local.repository.FavoriteMovieRepository
import com.naol.moviemania.data.remote.ApiKeyInterceptor
import com.naol.moviemania.data.remote.TMDBApi
import com.naol.moviemania.data.remote.TMDBApi.Companion.BASE_URL
import com.naol.moviemania.data.remote.repository.RemoteRepositoryImpl
import com.naol.moviemania.domain.repository.MovieManiaRepository
import com.naol.moviemania.domain.usecase.GetMovieCreditsUseCase
import com.naol.moviemania.domain.usecase.GetMovieDetailsUseCase
import com.naol.moviemania.domain.usecase.GetMoviesUseCase
import com.naol.moviemania.domain.usecase.IsFavMovieUseCase
import com.naol.moviemania.domain.usecase.RemoveFavMovieUseCase
import com.naol.moviemania.domain.usecase.SaveFavMovieUseCase
import com.naol.moviemania.domain.usecase.SearchMoviesUseCase
import com.naol.moviemania.presentation.home.HomeViewModel
import com.naol.moviemania.presentation.home.allmovies.AllMoviesViewModel
import com.naol.moviemania.presentation.home.nowplaying.NowPlayingViewModel
import com.naol.moviemania.presentation.home.popularmovies.PopularMoviesViewModel
import com.naol.moviemania.presentation.home.topratedmovies.TopRatedMoviesViewModel
import com.naol.moviemania.presentation.home.upcomingmovies.UpcomingMoviesViewModel
import com.naol.moviemania.presentation.moviedetail.MovieDetailsViewModel
import com.naol.moviemania.presentation.searchmovie.SearchMoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
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
        RemoteRepositoryImpl(get())
    }
}


val viewModelsModule = module {
    viewModel {
        HomeViewModel()
    }
    viewModel {
        TopRatedMoviesViewModel(get())
    }
    viewModel {
        NowPlayingViewModel(get(), get(), get(), get())
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
    viewModel {
        MovieDetailsViewModel(get(), get())
    }
    viewModel {
        PopularMoviesViewModel(get())
    }
}

val useCasesModule = module {
    single { GetMoviesUseCase(get(named("remote"))) }

    single { SearchMoviesUseCase(get(named("remote"))) }

    single { GetMovieDetailsUseCase(get(named("remote"))) }

    single { GetMovieCreditsUseCase(get(named("remote"))) }

    single { SaveFavMovieUseCase(get(named("local"))) }

    single { RemoveFavMovieUseCase(get(named("local"))) }

    single { IsFavMovieUseCase(get(named("local"))) }
}

val databaseModule = module {
    single(createdAtStart = true) {
        Room.databaseBuilder(
            androidApplication(),
            MovieManiaDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single<MovieDao> {
        get<MovieManiaDatabase>().movieDao()
    }

    single(qualifier = named("local")) {
        FavoriteMovieRepository(get<MovieManiaDatabase>().movieDao())
    }
}
