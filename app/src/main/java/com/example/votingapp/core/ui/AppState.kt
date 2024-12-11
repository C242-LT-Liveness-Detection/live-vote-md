package com.example.votingapp.core.ui

import android.content.res.Resources
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.votingapp.core.navigation.Destination
import com.example.votingapp.core.navigation.navigateToChoseVote
import com.example.votingapp.core.navigation.navigateToCreateVote
import com.example.votingapp.core.navigation.navigateToHome
import com.example.votingapp.core.navigation.navigateToJoinVote
import com.example.votingapp.core.navigation.navigateToLiveness
import com.example.votingapp.core.navigation.navigateToLogin
import com.example.votingapp.core.navigation.navigateToRegister
import com.example.votingapp.core.navigation.navigateToWelcome
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        navController,
        windowSizeClass,
        coroutineScope
    ) {
        AppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    val coroutineScope: CoroutineScope,

    ) {

    fun onBackClick() {
        navController.popBackStack()
    }

}
