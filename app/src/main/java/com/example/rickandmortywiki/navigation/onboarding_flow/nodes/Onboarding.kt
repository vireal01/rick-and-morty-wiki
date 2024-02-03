package com.example.rickandmortywiki.navigation.onboarding_flow.nodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickandmortywiki.navigation.Screens
import com.example.rickandmortywiki.ui.onboarding.OnboardingScreen
import com.example.rickandmortywiki.ui.onboarding.UserStateViewModel

fun NavGraphBuilder.onboarding(
    navController: NavController,
    userStateViewModel: UserStateViewModel
) {
    composable(route = Screens.Onboarding.route) {
        OnboardingScreen(
            onSkipOnboardingClick = {
                userStateViewModel.showOnboarding(false)
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
