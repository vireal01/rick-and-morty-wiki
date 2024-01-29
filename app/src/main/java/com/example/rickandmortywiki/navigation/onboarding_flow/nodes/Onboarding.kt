package com.example.rickandmortywiki.navigation.onboarding_flow.nodes

import OnboardingScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens

fun NavGraphBuilder.onboarding(navController: NavController) {
    composable(route = Screens.Onboarding.route) {
        OnboardingScreen(
            onSkipOnboardingClick = {
                navController.navigate(Screens.MainAppContent.route) {
                    launchSingleTop = true
                    popUpTo(Screens.Onboarding.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
