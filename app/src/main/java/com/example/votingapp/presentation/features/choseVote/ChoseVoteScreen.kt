package com.example.votingapp.presentation.features.choseVote

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.votingapp.core.navigation.homeNavigationRoute
import com.example.votingapp.core.navigation.navigateToHome
import com.example.votingapp.core.ui.theme.AppTheme
import com.example.votingapp.data.resource.remote.response.success.OptionsItem
import com.example.votingapp.data.resource.remote.response.success.VoteByCodeResponse
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.components.DialogType

@Composable
fun ChoseVoteRoute(
    navController: NavController,
    voteCode: String,
    viewModel: ChoseVoteViewModel = hiltViewModel(),
) {
    LaunchedEffect(voteCode) {
        viewModel.getVoteByCode(voteCode)
    }

    ChoseVoteScreen(
        navController,
        onEvent = { viewModel.onEvent(it) },
        loading = viewModel.loading.value,
        vote = viewModel.vote.value,
        selectedOption = viewModel.selectedOption.value,
        successMessage = viewModel.successMessage.value,
        errorMessages = viewModel.errorMessages.value
    )
}

@Composable
fun ChoseVoteScreen(
    navController: NavController,
    onEvent: (ChoseVoteEvent) -> Unit,
    loading: Boolean,
    vote: VoteByCodeResponse? = null,
    selectedOption: List<Int>,
    successMessage: String? = null,
    errorMessages: String? = null
) {

    successMessage?.let {
        DialogMessage(
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(ChoseVoteEvent.ClearSuccess)
                    navController.navigate(homeNavigationRoute) {

                        popUpTo(homeNavigationRoute) {
                            inclusive = true
                        }
                    }
                }) {
                    Text("Oke")
                }
            },
        )
    }

    errorMessages?.let {
        DialogMessage(
            dialogType = DialogType.ERROR,
            message = it,
            confirmButton = {
                TextButton({
                    onEvent(ChoseVoteEvent.ClearError)
                }) {
                    Text("Oke")
                }
            },
        )
    }



    if (loading || vote == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (bgTop, content) = createRefs()

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff38bdf8),
                            Color(0xff3b82f6)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .fillMaxWidth()
                .constrainAs(bgTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .height(200.dp)
                .padding(top = 50.dp, start = 16.dp)
        ) {
            Text(
                vote.question,
                style = MaterialTheme.typography.titleLarge.copy(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFB9E9FF),
                            Color(0xFFC5EBFF)
                        )
                    )
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = (LocalConfiguration.current.screenHeightDp.dp),
                    max = (LocalConfiguration.current.screenHeightDp.dp - 150.dp)
                )
                .shadow(elevation = 16.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp)
                .constrainAs(content) {
                    top.linkTo(bgTop.bottom, margin = (-70).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom) // Menjaga agar tetap terhubung ke bawah
                }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(vote.options.size) { index ->
                    val selected = selectedOption.contains(index)
                    VoteItem(
                        vote.options[index].optionText,
                        isSelected = selected,
                        onClick = {
                            if (selected) {
                                onEvent(ChoseVoteEvent.RemoveOption(index))
                            } else {
                                onEvent(ChoseVoteEvent.SelectOption(index))
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AppButton("Lanjut") {
                onEvent(ChoseVoteEvent.SubmitVote(vote.uniqueCode))
            }
        }
    }
    if (loading) {
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


@Composable
fun VoteItem(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val color = remember { Animatable(Color.Transparent) }
    val primaryColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color.value,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }

    // Trigger animation when isSelected changes
    LaunchedEffect(isSelected) {
        if (isSelected) {
            color.animateTo(primaryColor, tween(300))
        } else {
            color.animateTo(Color.Transparent, tween(300))
        }
    }
}


