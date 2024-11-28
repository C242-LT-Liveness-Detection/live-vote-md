package com.example.votingapp.presentation.features.createVoting

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.CreateVotingOption
import com.example.votingapp.presentation.components.DateInput
import com.example.votingapp.presentation.components.DialWithDialogExample
import com.example.votingapp.presentation.components.InputTextField
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CreateVoteRoute() {
    CreateVoteScreen()
}

@Composable
fun CreateVoteScreen() {
    var showVoteCreation by remember { mutableStateOf(false) }

    // Animasi transisi antar halaman
    AnimatedContent(
        targetState = showVoteCreation,
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        }
    ) { targetState ->
        if (targetState) {
            CreateVotePage(onBack = { showVoteCreation = false })
        } else {
            TitleDescriptionPage(onNext = { showVoteCreation = true })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleDescriptionPage(onNext: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    val calendarShow = remember { mutableStateOf(false) }

    var showTimePicker by remember { mutableStateOf(false) }
    val stateTimePicker = rememberTimePickerState(
        is24Hour = true
    )
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Input Title
        InputTextField(
            text = title,
            label = "Judul Voting",
            onValueChange = { title = it },

            )
        Spacer(modifier = Modifier.height(8.dp))

        // Input Description
        InputTextField(
            label = "Deskripsi Voting",
            text = description,
            onValueChange = { description = it },

            )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Waktu berakhir",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        calendarShow.value = true
                    },

                ) {
                Row {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )

                            .padding(8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )

                            .padding(8.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            datePickerState.selectedDateMillis?.let {
                                SimpleDateFormat("dd/MM/yyyy").format(it)
                            } ?: "Pilih Tanggal"
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        showTimePicker = true
                    }
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            Icons.Filled.AccessTime,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )

                            .padding(8.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text("${stateTimePicker.hour}:${stateTimePicker.minute}")
                    }

                }
            }


        }



        Spacer(modifier = Modifier.weight(1f))

        AppButton(text = "Lanjut") {
            onNext()
        }
    }
    DateInput(
        calendarShow.value,
        datePickerState,
        onOpenDialog = { calendarShow.value = true },
        onCloseDialog = { calendarShow.value = false })
    DialWithDialogExample(
        state = stateTimePicker,
        show = showTimePicker,
        onConfirm = { showTimePicker = false },
        onDismiss = { showTimePicker = false }

    )
}

@Composable
fun CreateVotePage(onBack: () -> Unit) {
    var voteOptions by remember { mutableStateOf(listOf("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Opsi Voting", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))



        voteOptions.forEachIndexed { index, it ->
            CreateVotingOption(value = it, onDelete = {
                voteOptions = voteOptions.toMutableList().also { list ->
                    list.removeAt(index)
                }
            }, onValueChange = {
                voteOptions = voteOptions.toMutableList().also { list ->
                    list[index] = it
                }
            }


            )
        }
        Button(
            onClick = {
                voteOptions = voteOptions + ""
            }
        ) {
            Text(text = "Tambah Opsi")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(Modifier.weight(1f))

        AppButton(text = "Buat") {

        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateVoteScreenPreview() {
    AppTheme {
        CreateVoteScreen()
    }
}