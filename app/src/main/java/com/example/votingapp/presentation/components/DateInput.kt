package com.example.votingapp.presentation.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    show: Boolean,
    datePickerState: DatePickerState,
    onOpenDialog: () -> Unit,
    onCloseDialog: () -> Unit
) {

    if (show) {
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(

            onDismissRequest = {
                onCloseDialog()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCloseDialog()

                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCloseDialog() }) { Text("Cancel") }
            }
        ) {

            DatePicker(
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState()),

                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DateInputPreview() {
    val datePickerState = rememberDatePickerState()
    val calendarShow = remember { mutableStateOf(true) }

    DateInput(
        calendarShow.value,
        datePickerState,
        onOpenDialog = { calendarShow.value = true },
        onCloseDialog = { calendarShow.value = false })
}