package com.example.votingapp.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//import android.app.TimePickerDialog
//import android.content.Context
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.TimePickerState
//import androidx.compose.runtime.Composable
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TimePickerInput(
//    context: Context,
//    showTimePicker: Boolean,
//    state: TimePickerState,
//    onOpenDialog: () -> Unit,
//    onCloseDialog: () -> Unit
//) {
//
//
//    if (showTimePicker) {
//        val timePickerDialog = TimePickerDialog(
//            context, { _, hour, minute ->
//                // Handle the selected time
//            }, state.hour, state.minute, true
//        )
//        timePickerDialog.show()
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    show: Boolean = false,
    state: TimePickerState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("OK")
                }
            },
            text = {
                TimePicker(
                    state = state,
                )
            }
        )
    }

}


@Composable
fun TimePickerDialog(
    show: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("OK")
                }
            },
            text = { content() }
        )
    }
}

