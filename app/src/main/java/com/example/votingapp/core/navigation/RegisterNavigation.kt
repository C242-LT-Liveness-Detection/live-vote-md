package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.register.RegisterRoute

const val registerNavigationRoute = "register_route"

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    this.navigate(registerNavigationRoute, navOptions)
}

fun NavGraphBuilder.registerScreen(
    navController: NavController
) {
    composable(route = registerNavigationRoute) {
        RegisterRoute(
            navController = navController
        )
    }
}