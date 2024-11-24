package com.example.votingapp.presentation.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.InputTextField

@Composable
fun JoinVotingScreen() {
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

        }
    }
}
