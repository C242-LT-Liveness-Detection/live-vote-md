package com.example.votingapp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.votingapp.presentation.features.choseVote.ChoseVoteRoute

const val choseVoteNavigationRoute = "chose_vote_route"
const val voteCodeArg = "voteCode"

fun NavController.navigateToChoseVote(voteCode: String, navOptions: NavOptions? = null) {
    this.navigate("$choseVoteNavigationRoute/$voteCode", navOptions)
}


//fun NavGraphBuilder.choseVoteScreen() {
//    composable(
//        route = "$choseVoteNavigationRoute/${voteCodeArg}",
//        arguments = listOf(navArgument(voteCodeArg) { type = NavType.StringType })
//    )
//    { backStackEntry ->
//        val voteCode = backStackEntry.arguments?.getString(voteCodeArg) ?: ""
//        ChoseVoteRoute(voteCode)
//    }
//}

fun NavGraphBuilder.choseVoteScreen() {
    composable(
        route = "$choseVoteNavigationRoute/{$voteCodeArg}",
        arguments = listOf(navArgument(voteCodeArg) { type = NavType.StringType })
    ) { backStackEntry ->
        val voteCode = backStackEntry.arguments?.getString(voteCodeArg) ?: ""
        ChoseVoteRoute(voteCode)
    }
}