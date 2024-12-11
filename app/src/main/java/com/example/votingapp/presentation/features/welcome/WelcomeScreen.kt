package com.example.votingapp.presentation.features.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.votingapp.R
import com.example.votingapp.core.ui.theme.AppTypography
import com.example.votingapp.presentation.components.AppButton

@Composable
internal fun WelcomeRoute(navigateToLogin: () -> Unit) {
    WelcomeScreen(
        navigateToLogin = navigateToLogin
    )
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.7f))

        AnimatedPreloader()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to Voting App",
            style = AppTypography.titleLarge,
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Cara Baru untuk Memilih dengan \n Aman dan Mudah!",
            style = AppTypography.labelLarge,
            minLines = 2,
            textAlign = TextAlign.Center,
        )

//     cara agar button berada di palang bawah
        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            text = "Get Started",
            modifier = Modifier
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(50),

            ) {
            navigateToLogin()
        }
        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.welcome
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,

        )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier.height(400.dp)
    )
}
