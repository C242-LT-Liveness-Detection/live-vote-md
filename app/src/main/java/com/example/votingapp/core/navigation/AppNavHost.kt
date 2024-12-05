package com.example.votingapp.core.navigation

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
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
        registerScreen()
        homeScreen(
            navigateToCreateVoting = {
                navController.navigate(createVoteNavigationRoute)
            },
            navigateToJoinVoting = {
                navController.navigate(joinVoteNavigationRoute)
            },

            navigateToLogin = {
                navController.navigate(loginNavigationRoute)
            }

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
    }
}
