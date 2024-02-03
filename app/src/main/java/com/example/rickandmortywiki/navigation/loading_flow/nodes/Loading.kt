package com.example.rickandmortywiki.navigation.loading_flow.nodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.ui.loadingScreen.LoadingScreen
import com.example.rickandmortywiki.ui.onboarding.UserStateViewModel

fun NavGraphBuilder.loading(
    navController: NavController,
    userStateViewModel: UserStateViewModel
) {
    composable(route = Screens.Loading.route) {
        LoadingScreen(
            goToMainScreen = {
                navController.navigate(Screens.MainAppContent.route) {
                    launchSingleTop = true
                    popUpTo(Screens.Loading.route) {
                        inclusive = true
                    }
                }
            },
            goToOnboardingScreen = {
                navController.navigate(Screens.Onboarding.route) {
                    launchSingleTop = true
                    popUpTo(Screens.Loading.route) {
                        inclusive = true
                    }
                }
            },
            viewModel = userStateViewModel
        )
    }
}
