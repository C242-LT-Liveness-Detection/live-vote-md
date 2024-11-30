package com.example.votingapp.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.votingapp.R
import com.example.votingapp.core.domain.models.VoteModel
import com.example.votingapp.presentation.components.RecentVotingItem

@Composable
internal fun HomeRoute(
    navigateToCreateVoting: () -> Unit,
    navigateToJoinVoting: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val listVote = viewModel.listVotes.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    LaunchedEffect(key1 = Unit) {
        viewModel.getVotes()
    }

    HomeScreen(
        listVote = listVote,
        errorMessage = errorMessage,
        isLoading = isLoading,
        viewModel = viewModel,
        onCreateVotingClick = navigateToCreateVoting,
        onJoinVotingClick = navigateToJoinVoting,
        navigateToLogin = navigateToLogin
    )
}

@Composable
fun HomeScreen(
    listVote: List<VoteModel>,
    errorMessage: String,
    isLoading: Boolean,
    viewModel: HomeViewModel,
    onCreateVotingClick: () -> Unit,
    onJoinVotingClick: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val dialogConfirmLogout = remember { mutableStateOf(false) }



    LazyColumn {
        item {
            ConstraintLayout() {
                val (topImg) = createRefs()

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .constrainAs(topImg) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)

                        }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xff38bdf8),
                                    Color(0xff3b82f6)

                                ),
                                start = Offset(0f, 0f),
                                end = Offset(1000f, 1000f)
                            ),
                            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        )
                )

                Box(
                    Modifier
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Hello, User!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,

                                )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.CenterEnd)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clickable { setShowDialog(true) },
                                    shape = CircleShape,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.avatar),
                                        contentDescription = "User Avatar",
                                        contentScale = ContentScale.Crop,
                                    )
                                }

                                DropdownMenu(
                                    expanded = showDialog,
                                    onDismissRequest = {
                                        setShowDialog(false)
                                    },


                                    ) {
                                    DropdownMenuItem(
                                        onClick = {},
                                        text = { Text("Pengaturan") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            dialogConfirmLogout.value = true

                                        },
                                        text = { Text("Keluar") }
                                    )

                                }
                            }


                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            CardFeature(
                                onClick = onCreateVotingClick,
                                text = "Create Voting",
                                icon = R.drawable.checklist
                            )

                            CardFeature(
                                onClick = onJoinVotingClick,
                                text = "Join Voting",
                                icon = R.drawable.vote
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                "Recent Voting",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                "See All",
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                    }
                }

            }

        }
        if (isLoading) {
            item {
                CircularProgressIndicator(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        } else {
            if (listVote.isEmpty()) {
                item {
                    Text(
                        text = "No Voting Available",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(listVote.size) { index ->
                    RecentVotingItem(
                        vote = listVote[index],

                        )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }


    if (dialogConfirmLogout.value) {
        LogoutDialog(
            onDismiss = {
                dialogConfirmLogout.value = false
            },
            onConfirm = {
                dialogConfirmLogout.value = false
                viewModel.logout()
                navigateToLogin()
            }
        )
    }

}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Logout")
        },
        text = {
            Text("Are you sure want to logout?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("No")
            }
        }
    )
}


@Composable
fun CardFeature(
    onClick: () -> Unit,
    text: String,
    icon: Int
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = MaterialTheme.shapes.medium
            )
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .size(150.dp)

            .clickable { onClick() }


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(Modifier.weight(1f))
            Image(
                painterResource(icon),
                contentDescription = "Create Voting",
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.weight(1f))
            Text(text, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
        }
    }
}

