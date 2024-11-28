package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.choseVote.ChoseVoteRoute

const val choseVoteNavigationRoute = "chose_vote_route"

fun NavController.navigateToChoseVote(navOptions: NavOptions? = null) {
    this.navigate(choseVoteNavigationRoute, navOptions)
}

fun NavGraphBuilder.choseVoteScreen(
) {
    composable(route = choseVoteNavigationRoute) {
        ChoseVoteRoute()
    }
}