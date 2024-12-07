package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.votingapp.presentation.features.detailVote.DetailVotingRoute

const val detailVoteNavigationRoute = "detail_vote_route"

fun NavController.navigateToDetailVote(voteCode: String, navOptions: NavOptions? = null) {
    this.navigate("$detailVoteNavigationRoute/$voteCode", navOptions)
}

fun NavGraphBuilder.detailVoteScreen(
    navController: NavController
) {
    composable(
        route = "$detailVoteNavigationRoute/{$voteCodeArg}",
        arguments = listOf(navArgument(voteCodeArg) { type = NavType.StringType })
    ) { backStackEntry ->
        val voteCode = backStackEntry.arguments?.getString(voteCodeArg) ?: ""
        DetailVotingRoute(
            navController = navController,
            voteCode = voteCode
        )
    }
}
