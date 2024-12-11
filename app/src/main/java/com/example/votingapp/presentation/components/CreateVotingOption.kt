package com.example.votingapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CreateVotingOption(
    value: String = "",
    onValueChange: (String) -> Unit,
//    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically

    ) {
        InputTextField(
            text = value,
            label = "Option",
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange
        )


    }
}