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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.HowToVote
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.votingapp.R
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.RecentVotingItem

@Composable
fun HomeRoute(
    navigateToCreateVoting: () -> Unit,
    navigateToJoinVoting: () -> Unit
) {
    HomeScreen(
        onCreateVotingClick = navigateToCreateVoting,
        onJoinVotingClick = navigateToJoinVoting
    )
}

@Composable
fun HomeScreen(
    onCreateVotingClick: () -> Unit,
    onJoinVotingClick: () -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
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
                        color = Color.White
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
                                onClick = {},
                                text = { Text("Keluar") }
                            )

                        }
                    }


                }
                Row {
                    CardFeature(
                        onClick = onCreateVotingClick,
                        text = "Create Voting",
                        icon = R.drawable.checklist
                    )
                    Spacer(modifier = Modifier.width(16.dp))
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

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(10) {
                        RecentVotingItem()
                    }
                }

            }
        }

    }


}


@Composable
fun CardFeature(
    onClick: () -> Unit,
    text: String,
    icon: Int
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
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

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            onCreateVotingClick = {},
            onJoinVotingClick = {}
        )
    }
}