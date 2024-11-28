package com.example.votingapp.presentation.features.joinVote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.InputTextField

@Composable
fun JoinVotingRoute(
    onJoin: () -> Unit
) {
    JoinVotingScreen(
        onJoin = onJoin
    )
}

@Composable
fun JoinVotingScreen(
    modifier: Modifier = Modifier,
    onJoin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputTextField(text = "", label = "Masukkan kode") {

        }

        Spacer(modifier = Modifier.height(10.dp))

        AppButton(
            text = "Join",
            modifier = Modifier.padding(50.dp, 0.dp)
        ) {
            onJoin()
        }
    }
}
