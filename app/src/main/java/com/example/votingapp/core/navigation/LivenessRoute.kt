package com.example.votingapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.votingapp.presentation.features.liveness.LivenessRoute

const val livenessNavigationRoute = "liveness_route"


fun NavController.navigateToLiveness(
    voteCode: String,
    navOptions: NavOptions? = null
) {
    this.navigate("$livenessNavigationRoute/$voteCode", navOptions)
}

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.livenessScreen(
    navController: NavController
) {
    composable(
        route = "$livenessNavigationRoute/{$voteCodeArg}",
        arguments = listOf(navArgument(voteCodeArg) { type = NavType.StringType })
    ) { backStackEntry ->
        val voteCode = backStackEntry.arguments?.getString(voteCodeArg) ?: ""

        LivenessRoute(
            navController = navController,
            voteCode = voteCode
        )
    }
}