package com.example.rickandmortywiki.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.navigation.main_flow.mainFlow
import com.example.rickandmortywiki.navigation.onboarding_flow.nodes.onboarding
import com.example.rickandmortywiki.ui.onboarding.UserStateViewModel

@Composable
fun RickAndMortyApp(
    viewModel: UserStateViewModel = hiltViewModel(),
) {
    val isShowOnboarding = viewModel.showOnboarding.collectAsState().value
    viewModel.showUserPreferencesFlow()
    val startDestination = if (isShowOnboarding) Screens.Onboarding else Screens.MainAppContent

    val navController = rememberNavController()

    RickAndMortyHost(
        navController = navController,
        startDestination = startDestination,
        userStateViewModel = viewModel
    )
}

@Composable
fun RickAndMortyHost(
    navController: NavHostController,
    startDestination: Screens,
    userStateViewModel: UserStateViewModel
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        //TODO: Onboarding screen show for a sec until the DataStore value reads. Fix it with splash??
        onboarding(navController, userStateViewModel)
        mainFlow(navController)
    }
}
