package com.example.rickandmortywiki.ui.loadingScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickandmortywiki.ui.onboarding.UserStateViewModel
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
@Composable
fun LoadingScreen(
    viewModel: UserStateViewModel,
    goToOnboardingScreen: () -> Unit,
    goToMainScreen: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(true) {
            val showOnboardingState = viewModel.getShowOnboardingState()
            // It's too fast otherwise (:
            delay(500)
            if (showOnboardingState) {
                goToOnboardingScreen()
            } else {
                goToMainScreen()
            }
        }

        return CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
