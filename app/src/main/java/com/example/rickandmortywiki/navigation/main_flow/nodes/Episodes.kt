package com.example.rickandmortywiki.navigation.main_flow.nodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.ui.episodes.EpisodesListRenderer

fun NavGraphBuilder.episodes(navController: NavController) {
    composable(
        route = Screens.Episodes.route,
        arguments = Screens.Episodes.navArgument

    ) { navBackStackEntity ->
        EpisodesListRenderer(
            onEpisodeClick = {
                navController.navigate(
                    Screens.Characters.createRoute(
                        episodeId = it.episodeId
                    )
                )
            }
        )
    }
}
