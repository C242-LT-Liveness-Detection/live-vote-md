package com.example.votingapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(
            navcontroller = navController
        )
        registerScreen(
            navController = navController
        )
        homeScreen(
            navController = navController

        )
        createVoteScreen(
            navController = navController
        )
        joinVoteScreen(
            navController = navController
        )
        choseVoteScreen(
            navController
        )
        welcomeScreen(
            navigateToLogin = {
                navController.navigate(loginNavigationRoute)
            }
        )

        livenessScreen(
            navController = navController
        )

        detailVoteScreen(
            navController = navController
        )
    }
}
