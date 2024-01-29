package com.example.rickandmortywiki.navigation.main_flow.nodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoRenderer

fun NavGraphBuilder.characterInfo(navController: NavController) {
    composable(route = Screens.CharacterInfo.route) {
        CharacterInfoRenderer(
            characterId = navController.currentBackStackEntry?.arguments?.getString("characterId")
                ?.toInt(),
            onBackClick = { navController.navigateUp() },
        )
    }
}
