package com.example.votingapp.presentation.features.joinVote

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.votingapp.core.navigation.choseVoteNavigationRoute
import com.example.votingapp.core.navigation.navigateToChoseVote
import com.example.votingapp.core.navigation.navigateToJoinVote
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.components.DialogType
import com.example.votingapp.presentation.components.InputTextField
import com.example.votingapp.presentation.features.createVoting.CreateVoteEvent

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
        navController = navController
    )
}

@Composable
fun JoinVotingScreen(
    modifier: Modifier = Modifier,
    onEvent: (JoinVoteEvent) -> Unit,
    code: String,
    errorMessage: String? = null,
    successMessage: String? = null,
    navController: NavController
) {

    successMessage?.let {
        DialogMessage(
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(JoinVoteEvent.ClearSuccess)
                    navController.navigateToChoseVote(code)
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
}
