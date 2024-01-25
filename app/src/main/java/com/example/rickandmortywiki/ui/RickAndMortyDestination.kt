package com.example.rickandmortywiki.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class RickAndMortyDestination(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()

) {
    data object Episodes : RickAndMortyDestination("episodes")

    data object Characters : RickAndMortyDestination(
        route = "characters/{episodeId}",
        navArgument = listOf(navArgument("episodeId") {
            type = NavType.IntType
        })
    ) {
        fun createRoute(episodeId: Int) = "characters/$episodeId"
    }

    data object CharacterInfo : RickAndMortyDestination(
        route = "characterInfo/{characterId}",
        navArgument = listOf(navArgument("characterId") {
            type = NavType.IntType
        }),
    ) {
        fun createRoute(characterId: Int) = "characterInfo/$characterId"
    }
}
