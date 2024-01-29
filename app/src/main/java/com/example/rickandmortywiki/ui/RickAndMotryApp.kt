package com.example.rickandmortywiki.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.navigation.main_flow.mainFlow
import com.example.rickandmortywiki.navigation.onboarding_flow.nodes.onboarding

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
        startDestination = Screens.Onboarding.route
    ) {
        // TODO: The onboarding screen should opens only once (store state in shared preferences)
        onboarding(navController)
        mainFlow(navController)
    }
}
