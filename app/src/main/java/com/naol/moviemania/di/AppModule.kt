package com.naol.moviemania.di


import com.naol.moviemania.data.api.TMDBApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TMDBApi.BASE_URL)
            .build()
            .create(TMDBApi::class.java)
    }
}
