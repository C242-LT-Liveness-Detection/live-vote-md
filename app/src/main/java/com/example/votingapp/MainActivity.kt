package com.example.votingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.votingapp.core.navigation.choseVoteNavigationRoute
import com.example.votingapp.core.navigation.homeNavigationRoute
import com.example.votingapp.core.navigation.joinVoteNavigationRoute
import com.example.votingapp.core.navigation.welcomeNavigationRoute
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.core.ui.AndroidTemplateApp
import com.example.votingapp.core.ui.rememberAppState
import com.example.votingapp.data.storage.UserPreferenceStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferenceStore: UserPreferenceStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = runBlocking {
            val token = userPreferenceStore.accessToken.first()
            if (token.isNullOrEmpty()) {
                welcomeNavigationRoute
            } else {
                homeNavigationRoute
            }
        }

        setContent {
            AppTheme {
                AndroidTemplateApp(
                    startDestination = startDestination,
                    appState = rememberAppState(
                        windowSizeClass = calculateWindowSizeClass(this)
                    ),
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
