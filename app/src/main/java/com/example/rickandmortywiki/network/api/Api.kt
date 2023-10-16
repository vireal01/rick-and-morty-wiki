package com.example.rickandmortywiki.network.api

import com.example.rickandmortywiki.network.models.AllEpisodes
import com.example.rickandmortywiki.network.models.Character
import com.example.rickandmortywiki.network.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") id: String): List<Character>

    @GET("episode")
    suspend fun getAllEpisodes(): AllEpisodes

    @GET("episode")
    suspend fun getEpisodesPage(@Query("page") page: Int): AllEpisodes

    @GET
    suspend fun getEpisodesByUrl(@Url url: String): AllEpisodes

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Episode
}
