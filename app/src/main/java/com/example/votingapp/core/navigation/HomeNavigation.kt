package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.home.HomeRoute

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToCreateVoting: () -> Unit,
    navigateToJoinVoting: () -> Unit,
    navigateToLogin: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeRoute(
            navigateToCreateVoting = navigateToCreateVoting,
            navigateToJoinVoting = navigateToJoinVoting,
            navigateToLogin = navigateToLogin
        )
    }
}
