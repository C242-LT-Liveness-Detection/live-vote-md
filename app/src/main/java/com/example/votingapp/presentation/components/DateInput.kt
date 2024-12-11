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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
                title = { Text("Pilih Tanggal") },

                headline = { Text("Batas Akhir") },
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState()),

                )
        }
    }
}
