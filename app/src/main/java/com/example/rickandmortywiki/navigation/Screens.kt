package com.example.rickandmortywiki.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()

) {
    data object Onboarding : Screens("onboarding")

    data object MainAppContent : Screens("main_app_content")

    data object Episodes : Screens("episodes")

    data object Characters : Screens(
        route = "characters/{episodeId}",
        navArgument = listOf(navArgument("episodeId") {
            type = NavType.IntType
        })
    ) {
        fun createRoute(episodeId: Int) = "characters/$episodeId"
    }

    data object CharacterInfo : Screens(
        route = "characterInfo/{characterId}",
        navArgument = listOf(navArgument("characterId") {
            type = NavType.IntType
        }),
    ) {
        fun createRoute(characterId: Int) = "characterInfo/$characterId"
    }
}
