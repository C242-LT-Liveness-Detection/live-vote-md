package com.example.votingapp.presentation.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.votingapp.core.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    show: Boolean,
    datePickerState: DatePickerState,
    onCloseDialog: () -> Unit,
    confirmButton: @Composable () -> Unit
) {

    if (show) {

        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = Color.White
            ),
            onDismissRequest = {
                onCloseDialog()
            },
            confirmButton = confirmButton,
            dismissButton = {
                TextButton(onClick = { onCloseDialog() }) { Text("Cancel") }
            }
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White
                ),
                headline = { Text("Batas Akhir") },
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState()),

                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun DateInputPreview() {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
    )
    AppTheme {
        DateInput(
            show = true,
            datePickerState = datePickerState,
            onCloseDialog = {},
            confirmButton = {},

            )
    }
}
