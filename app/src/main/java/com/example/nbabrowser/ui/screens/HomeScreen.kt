package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.ui.theme.NBABrowserTheme

@Composable
fun HomeScreen(
    uiState: NBAUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (uiState) {
        is NBAUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is NBAUiState.Success -> ResultScreen(
            uiState.players, modifier = modifier.fillMaxWidth().padding(contentPadding)
        )
        is NBAUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ResultScreen(players: List<Player>, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = players,
                key = { player -> player.id }
            ) { player ->
                PlayerItem(player = player)
            }
        }
    }
}

@Composable
fun PlayerItem(player: Player, modifier: Modifier = Modifier) {
    Card (
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Text("${player.firstName} ${player.lastName}")
            Text(player.position)
            Text(player.team.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    NBABrowserTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    NBABrowserTheme {
        ErrorScreen()
    }
}
