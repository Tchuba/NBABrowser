@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.model.Team
import com.example.nbabrowser.ui.AppViewModelProvider
import com.example.nbabrowser.ui.TopAppBar
import com.example.nbabrowser.ui.navigation.NavigationDestination
import com.example.nbabrowser.ui.shared.ErrorScreen
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
            contentPadding = PaddingValues(dimensionResource(R.dimen.horizontal_padding)),
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
            .padding(vertical = dimensionResource(R.dimen.small_padding))
    ) {
        Row() {
            GlideImage(
                model = "https://variety.com/wp-content/uploads/2021/07/Rick-Astley-Never-Gonna-Give-You-Up.png",
                contentDescription = "Player image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(dimensionResource(R.dimen.small_photo_size))
            )
            Column (
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding)).weight(1f)
            ) {
                Text(
                    text = "${player.firstName} ${player.lastName}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = player.position,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    player.team.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PlayerItemPreview() {
    NBABrowserTheme {
        val player = Player(
            1,
            "Alex",
            "Abrines",
            "G",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            Team(1, "", "", "", "Thunder", "", "")
        )

        PlayerItem(player)
    }
}
