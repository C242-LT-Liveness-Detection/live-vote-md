package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.welcome.WelcomeRoute

const val welcomeNavigationRoute = "welcome_route"

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) {
    this.navigate(welcomeNavigationRoute, navOptions)
}

fun NavGraphBuilder.welcomeScreen(
    navigateToLogin: () -> Unit
) {
    composable(route = welcomeNavigationRoute) {
        WelcomeRoute(
            navigateToLogin = {
                navigateToLogin()
            }
        )
    }
}