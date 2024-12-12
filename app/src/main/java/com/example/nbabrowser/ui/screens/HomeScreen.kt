package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.ui.AppViewModelProvider
import com.example.nbabrowser.ui.TopAppBar
import com.example.nbabrowser.ui.navigation.NavigationDestination
import com.example.nbabrowser.ui.shared.ErrorScreen
import com.example.nbabrowser.ui.shared.LoadingScreen
import com.example.nbabrowser.ui.theme.NBABrowserTheme

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToPlayer:(Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(
            title = HomeDestination.titleRes,
            scrollBehavior = scrollBehavior,
            canNavigateBack = false
        ) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeBody(uiState = uiState, navigateToPlayer =  navigateToPlayer, contentPadding = it)
        }
    }
}

@Composable
fun HomeBody(
    uiState: HomeUiState,
    navigateToPlayer: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (uiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize().padding(contentPadding))
        is HomeUiState.Success -> ResultScreen(
            players = uiState.players,
            onPlayerClick = { navigateToPlayer(it.id) },
            modifier = modifier.fillMaxWidth(),
            contentPadding = contentPadding
        )
        is HomeUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize().padding(contentPadding))
    }
}

@Composable
fun ResultScreen(
    players: List<Player>,
    onPlayerClick: (Player) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            items(
                items = players,
                key = { player -> player.id }
            ) { player ->
                PlayerItem(player = player, modifier = Modifier.clickable { onPlayerClick(player) })
            }
        }
    }
}

@Composable
fun PlayerItem(player: Player, modifier: Modifier = Modifier) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
