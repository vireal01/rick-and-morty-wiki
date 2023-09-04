package com.example.rickandmortywiki.network.api

import com.example.rickandmortywiki.network.models.AllEpisodes
import com.example.rickandmortywiki.network.models.Character
import com.example.rickandmortywiki.network.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    @GET("episode")
    suspend fun getAllEpisodes(): AllEpisodes

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Episode
}
