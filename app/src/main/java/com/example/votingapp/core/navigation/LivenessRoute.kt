package com.example.votingapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.votingapp.presentation.features.liveness.LivenessRoute

const val livenessNavigationRoute = "liveness_route"


fun NavController.navigateToLiveness(
    navOptions: NavOptions? = null
) {
    this.navigate(livenessNavigationRoute)
}

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.livenessScreen(
    navController: NavController
) {
    composable(route = livenessNavigationRoute) {
        LivenessRoute(
            navController = navController
        )
    }
}