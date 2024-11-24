package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.home.HomeRoute
import com.example.votingapp.presentation.features.welcome.WelcomeRoute

const val welcomeNavigationRoute = "home_route"

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.welcomScreen(
    navigateToLogin: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        WelcomeRoute(
            navigateToLogin = {
                navigateToLogin()
            }
        )
    }
}
