package com.example.rickandmortywiki.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmortywiki.data.databse.AppDatabase
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.network.CurlInterceptorWrapper
import com.example.rickandmortywiki.network.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(CurlInterceptorWrapper())
        .build()

    @Singleton
    @Provides
    fun db(context: Application): DatabaseApi = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "rick_and_morty_database"
    ).build()

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit) = retrofit.create(Api::class.java)
}
