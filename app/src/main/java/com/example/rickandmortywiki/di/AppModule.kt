package com.example.rickandmortywiki.di

import com.example.rickandmortywiki.network.CurlInterceptorWrapper
import com.example.rickandmortywiki.network.api.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {
    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(CurlInterceptorWrapper())
        .build()

    @Provides
    fun retrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    fun apiService(retrofit: Retrofit) = retrofit.create(Api::class.java)
}