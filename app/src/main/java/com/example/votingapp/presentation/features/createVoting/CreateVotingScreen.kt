package com.example.votingapp.presentation.features.createVoting

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.votingapp.core.navigation.navigateToHome
import com.example.votingapp.core.utils.PastOrPresentSelectableDates
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.CreateVotingOption
import com.example.votingapp.presentation.components.DateInput
import com.example.votingapp.presentation.components.DialWithDialogExample
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.components.DialogSuccessCreateVote
import com.example.votingapp.presentation.components.DialogType
import com.example.votingapp.presentation.components.InputTextField
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CreateVoteRoute(
    navController: NavController,
    viewModel: CreateVoteModel = hiltViewModel()
) {
    val createVoteUiInfo = viewModel.createVoteUiInfo.collectAsStateWithLifecycle().value
    CreateVoteScreen(
        navController = navController,
        createVoteUiInfo = createVoteUiInfo,
        calendarShow = viewModel.showCalendar.collectAsStateWithLifecycle().value,
        timePickerShow = viewModel.showTimePicker.collectAsStateWithLifecycle().value,
        onEvent = { viewModel.onEvent(it) },
        successMessage = viewModel.successMessage.collectAsStateWithLifecycle().value,
        errorMessage = viewModel.errorMessage.collectAsStateWithLifecycle().value,
        isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateVoteScreen(
    navController: NavController,
    createVoteUiInfo: CreateVoteUiInfo,
    calendarShow: Boolean,
    timePickerShow: Boolean,
    onEvent: (CreateVoteEvent) -> Unit,
    successMessage: String? = null,
    errorMessage: String? = null,
    isLoading: Boolean
) {
    var showVoteCreation by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = createVoteUiInfo.endDate,
        selectableDates = PastOrPresentSelectableDates
    )
    val stateTimePicker = rememberTimePickerState(
        is24Hour = true
    )

    successMessage?.let {
        DialogSuccessCreateVote(
            code = it,

            confirmButton = {
                TextButton(
                    onClick = {
                        navController.navigateToHome()
                    }
                ) {
                    Text("Oke")
                }

            }
        )
    }

    errorMessage?.let {
        DialogMessage(
            dialogType = DialogType.ERROR,
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(CreateVoteEvent.ClearError)
                }) {
                    Text("Oke")
                }
            },
        )
    }

    // Animasi transisi antar halaman
    AnimatedContent(
        targetState = showVoteCreation,
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        }, label = ""
    ) { targetState ->
        if (targetState) {
            BackHandler {
                showVoteCreation = false
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Opsi Voting", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))



                createVoteUiInfo.options.forEachIndexed { index, it ->
                    CreateVotingOption(
                        value = it,

                        onValueChange = {
                            onEvent(CreateVoteEvent.OptionOnChage(it, index))
                        },


                        )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Spacer(Modifier.weight(1f))

                AppButton(text = "Buat") {
                    onEvent(CreateVoteEvent.CreateVote)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Input Title
                InputTextField(
                    text = createVoteUiInfo.title,
                    label = "Judul Voting",
                    onValueChange = {
                        onEvent(CreateVoteEvent.OnValueChange(it, "title"))
                    },

                    )
                Spacer(modifier = Modifier.height(8.dp))

                // Input Description
                InputTextField(
                    label = "Pertanyaan",
                    text = createVoteUiInfo.question,
                    onValueChange = { onEvent(CreateVoteEvent.OnValueChange(it, "question")) },

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
                                onEvent(CreateVoteEvent.ToggleCalendar(true))
                            },

                        ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            bottomStart = 8.dp
                                        )
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
                                    createVoteUiInfo.endDate.let {
                                        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                            it
                                        )
                                    }
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onEvent(CreateVoteEvent.ToggleTimePicker(true))
                            }
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            bottomStart = 8.dp
                                        )
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
                                Text(
                                    createVoteUiInfo.endTime
                                )
                            }

                        }
                    }


                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Pilih lebih dari satu",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(
                        checked = createVoteUiInfo.allowMultipleSelection,
                        onCheckedChange = {
                            onEvent(
                                CreateVoteEvent.OnValueChange(
                                    it.toString(),
                                    "allowMultipleSelection"
                                )
                            )
                        },

                        )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                AppButton(text = "Lanjut") {
                    showVoteCreation = true
                }
            }



            DateInput(
                calendarShow,
                datePickerState,
                onCloseDialog = { onEvent(CreateVoteEvent.ToggleCalendar(false)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(CreateVoteEvent.OnDateSelected(datePickerState.selectedDateMillis!!))
                            onEvent(CreateVoteEvent.ToggleCalendar(false))

                        },

                        ) {
                        Text("OK")
                    }
                }
            )
            DialWithDialogExample(
                state = stateTimePicker,
                show = timePickerShow,
                onConfirm = {
                    onEvent(
                        CreateVoteEvent.OnTimeSelected(
                            time = "${stateTimePicker.hour.toString().padStart(2, '0')}:${
                                stateTimePicker.minute.toString().padStart(2, '0')
                            }"
                        )
                    )
                    onEvent(CreateVoteEvent.ToggleTimePicker(false))
                },
                onDismiss = { onEvent(CreateVoteEvent.ToggleTimePicker(false)) }

            )
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
