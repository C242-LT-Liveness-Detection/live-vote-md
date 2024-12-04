package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.createVoting.CreateVoteRoute

const val createVoteNavigationRoute = "create_vote_route"

fun NavController.navigateToCreateVote(navOptions: NavOptions? = null) {
    this.navigate(createVoteNavigationRoute, navOptions)
}

fun NavGraphBuilder.createVoteScreen(
    navController: NavController

) {
    composable(route = createVoteNavigationRoute) {
        CreateVoteRoute(
            navController = navController

        )
    }
}