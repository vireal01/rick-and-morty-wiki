package com.example.rickandmortywiki.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoRenderer
import com.example.rickandmortywiki.ui.charactersList.CharactersListRenderer
import com.example.rickandmortywiki.ui.episodes.EpisodesListRenderer

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()
    RickAndMortyHost(
        navController = navController
    )
}

@Composable
fun RickAndMortyHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RickAndMortyDestination.Episodes.route
    ) {
        composable(
            route = RickAndMortyDestination.Episodes.route,
            arguments = RickAndMortyDestination.Episodes.navArgument

        ) { navBackStackEntity ->
            EpisodesListRenderer(
                onEpisodeClick = {
                    navController.navigate(
                        RickAndMortyDestination.Characters.createRoute(
                            episodeId = it.episodeId
                        )
                    )
                }
            )
        }

        composable(
            route = RickAndMortyDestination.Characters.route,
            arguments = RickAndMortyDestination.Characters.navArgument
        ) {
            navController.currentBackStackEntry?.arguments?.getInt("episodeId")?.let { it1 ->
                CharactersListRenderer(
                    onBackClick = { navController.navigateUp() },
                    episodeId = it1,
                    onCharacterClick = {
                        navController.navigate(
                            RickAndMortyDestination.CharacterInfo.createRoute(
                                characterId = it.characterId
                            )
                        )
                    }
                )
            }
        }

        composable(route = RickAndMortyDestination.CharacterInfo.route) {
            navController.currentBackStackEntry?.arguments?.getString("characterId")?.let { it1 ->
                CharacterInfoRenderer(
                    characterId = it1.toInt(),
                    onBackClick = { navController.navigateUp() },
                )
            }
        }
    }
}
