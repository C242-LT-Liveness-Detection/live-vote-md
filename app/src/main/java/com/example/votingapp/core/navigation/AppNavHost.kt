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
    startDestination: String = welcomeNavigationRoute
) {
    Log.d("AppNavHost", "Start destination: $startDestination")
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(
            navigateToHome = {
                Log.d("AppNavHost", "navigateToHome")
                navController.navigate(homeNavigationRoute)
//                navController.navigate(homeNavigationRoute)
            }
        )
        registerScreen()
        homeScreen(
            navigateToCreateVoting = {
                navController.navigate(createVoteNavigationRoute)
            },
            navigateToJoinVoting = {
                navController.navigate(joinVoteNavigationRoute)
            }
        )
        createVoteScreen()
        joinVoteScreen(
            navigateToChoseVoting = {
                navController.navigate(choseVoteNavigationRoute)
            }
        )
        choseVoteScreen()
        welcomeScreen(
            navigateToLogin = {
                navController.navigate(loginNavigationRoute)
            }
        )
    }
}
