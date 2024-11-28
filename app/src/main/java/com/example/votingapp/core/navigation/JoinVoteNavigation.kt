package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.joinVote.JoinVotingRoute
import com.example.votingapp.presentation.features.joinVote.JoinVotingScreen

const val joinVoteNavigationRoute = "join_vote_route"

fun NavController.navigateToJoinVote(navOptions: NavOptions? = null) {
    this.navigate(joinVoteNavigationRoute, navOptions)
}

fun NavGraphBuilder.joinVoteScreen(
    navigateToChoseVoting: () -> Unit
) {
    composable(route = joinVoteNavigationRoute) {
        JoinVotingRoute(
            onJoin = {
                navigateToChoseVoting()
            }
        )
    }
}