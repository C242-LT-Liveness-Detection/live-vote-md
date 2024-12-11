package com.example.votingapp.presentation.features.joinVote

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.votingapp.core.navigation.navigateToLiveness
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.components.DialogType
import com.example.votingapp.presentation.components.InputTextField

@Composable
fun JoinVotingRoute(
    navController: NavController,
    viewModel: JoinVotingViewModel = hiltViewModel()
) {
    JoinVotingScreen(
        onEvent = { viewModel.onEvent(it) },
        code = viewModel.code.value,
        errorMessage = viewModel.errorMessage.value,
        successMessage = viewModel.successMessage.value,
        navController = navController,
        isLoading = viewModel.loading.value
    )
}

@Composable
fun JoinVotingScreen(
    onEvent: (JoinVoteEvent) -> Unit,
    code: String,
    errorMessage: String? = null,
    successMessage: String? = null,
    navController: NavController,
    isLoading: Boolean
) {

    successMessage?.let {
        DialogMessage(
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(JoinVoteEvent.ClearSuccess)
                    navController.navigateToLiveness(code)
                }) {
                    Text("Oke")
                }
            },
        )
    }

    errorMessage?.let {
        DialogMessage(
            dialogType = DialogType.ERROR,
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(JoinVoteEvent.ClearError)
                }) {
                    Text("Oke")
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputTextField(text = code, label = "Masukkan kode") {
            onEvent(JoinVoteEvent.InputOnChanged(it))
        }

        Spacer(modifier = Modifier.height(10.dp))

        AppButton(
            text = "Join",
            modifier = Modifier.padding(50.dp, 0.dp)
        ) {
            onEvent(JoinVoteEvent.JoinVote)
        }
    }

    if (isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {}
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
