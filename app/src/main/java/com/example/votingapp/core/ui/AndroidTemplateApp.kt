package com.example.votingapp.core.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.votingapp.core.navigation.AppNavHost
import com.example.votingapp.core.navigation.Destination

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AndroidTemplateApp(
    appState: AppState,
    startDestination: String
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },

        containerColor = Color(0xFFF7F7F7),
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier
                    .systemBarsPadding()
                    .navigationBarsPadding()
            )
        },


        ) { padding ->

        AppNavHost(
            startDestination = startDestination,
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            modifier = Modifier
                .padding(padding)

                .systemBarsPadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: Destination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false