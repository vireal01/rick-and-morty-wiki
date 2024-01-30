package com.example.rickandmortywiki.navigation.main_flow.nodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.ui.charactersList.CharactersListRenderer

fun NavGraphBuilder.charactersList(navController: NavController) {
    composable(
        route = Screens.Characters.route,
        arguments = Screens.Characters.navArgument
    ) {
        CharactersListRenderer(
            onBackClick = { navController.navigateUp() },
            episodeId = navController.currentBackStackEntry?.arguments?.getInt("episodeId"),
            onCharacterClick = {
                navController.navigate(
                    Screens.CharacterInfo.createRoute(
                        characterId = it.characterId
                    )
                )
            }
        )
    }
}
