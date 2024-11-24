package com.example.votingapp.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HowToVote
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.votingapp.R
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.RecentVotingItem

@Composable
fun HomeRoute(
    navigateToCreateVoting: () -> Unit = {},
    navigateToJoinVoting: () -> Unit = {}
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
            )

            Card(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                )
            }

        }
        Row {
            CardFeature(onClick = onCreateVotingClick)
            Spacer(modifier = Modifier.width(16.dp))
            CardFeature(onClick = onJoinVotingClick)
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
                style = MaterialTheme.typography.headlineSmall
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


@Composable
fun CardFeature(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .size(150.dp)


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Icon(Icons.Rounded.HowToVote, contentDescription = "Voting App")
            Text("Join")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen({}, {})
    }
}