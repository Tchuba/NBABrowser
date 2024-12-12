package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.ui.AppViewModelProvider
import com.example.nbabrowser.ui.TopAppBar
import com.example.nbabrowser.ui.navigation.NavigationDestination
import com.example.nbabrowser.ui.shared.ErrorScreen
import com.example.nbabrowser.ui.shared.LoadingScreen
import com.example.nbabrowser.ui.shared.SimpleLabel
import com.example.nbabrowser.ui.shared.ValueWithLabel

object PlayerDestination: NavigationDestination {
    override val route: String = "player_detail"
    const val playerIdArg = "playerId"
    val routeWithArgs = "$route/{$playerIdArg}"
    override val titleRes: Int = R.string.player_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    onNavigateUp: () -> Unit,
    navigateToTeam: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(
            title = PlayerDestination.titleRes,
            canNavigateBack = true,
            navigateUp = onNavigateUp
        ) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            PlayerBody(
                onTeamClick = { navigateToTeam(it.team.id) },
                uiState = uiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}

@Composable
fun PlayerBody(onTeamClick: (Player) -> Unit, uiState: PlayerUiState, modifier: Modifier = Modifier) {
    when (uiState) {
        is PlayerUiState.Loading -> LoadingScreen(modifier = modifier)
        is PlayerUiState.Success -> PlayerDetail(
            onTeamClick = onTeamClick,
            player = uiState.player,
            modifier = modifier
        )
        is PlayerUiState.Error -> ErrorScreen( modifier = modifier)
    }
}

@Composable
fun PlayerDetail(onTeamClick: (Player) -> Unit, player: Player, modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        ValueWithLabel(R.string.position_label, player.position)

        Column(modifier = Modifier.clickable { onTeamClick(player) }) {
            SimpleLabel(R.string.team_label)
            Row {
                Text(player.team.name)
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = stringResource(R.string.team_detail)
                )
            }
        }
    }
}
