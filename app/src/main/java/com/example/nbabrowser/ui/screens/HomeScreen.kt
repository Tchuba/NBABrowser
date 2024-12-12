@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
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
    val players = viewModel.playersPagedData.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(
            title = HomeDestination.titleRes,
            scrollBehavior = scrollBehavior,
            canNavigateBack = false
        ) }
    ) {
        HomeBody(
            players = players,
            navigateToPlayer = navigateToPlayer,
            modifier = Modifier.padding(
                top = it.calculateTopPadding()
            )
        )
    }
}

@Composable
fun HomeBody(
    players: LazyPagingItems<Player>,
    navigateToPlayer: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResultScreen(
        players = players,
        onPlayerClick = { navigateToPlayer(it.id) },
        modifier = modifier,
    )
}

@Composable
fun ResultScreen(
    players: LazyPagingItems<Player>,
    onPlayerClick: (Player) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing by remember {
        derivedStateOf { players.loadState.refresh is LoadState.Loading }
    }
//    val isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox (
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = { players.refresh() },
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        when (players.loadState.refresh) {
            is LoadState.Error -> ErrorScreen( modifier = Modifier.fillMaxSize())
            else -> {}
        }

        LazyColumn (
            state = rememberLazyListState(),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(players.itemCount, key = players.itemKey { it.id }) { index ->
                val player = players[index]

                if (player != null) {
                    PlayerItem(player = player, modifier = Modifier.clickable { onPlayerClick(player) })
                }
            }
        }

        when (players.loadState.append) {
            is LoadState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            else -> {}
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
