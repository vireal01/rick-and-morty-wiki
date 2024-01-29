package com.example.rickandmortywiki.navigation.main_flow

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.rickandmortywiki.navigation.main_flow.nodes.characterInfo
import com.example.rickandmortywiki.navigation.main_flow.nodes.charactersList
import com.example.rickandmortywiki.navigation.main_flow.nodes.episodes
import com.example.rickandmortywiki.navigation.Screens

fun NavGraphBuilder.mainFlow(navController: NavController) {
    navigation(
        startDestination = Screens.Episodes.route,
        route = Screens.MainAppContent.route
    ) {
        episodes(navController)
        charactersList(navController)
        characterInfo(navController)
    }
}
