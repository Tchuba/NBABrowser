package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Player
import com.example.nbabrowser.model.Team
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
                onTeamClick = { navigateToTeam(it.id) },
                uiState = uiState,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun PlayerBody(onTeamClick: (Team) -> Unit, uiState: PlayerUiState, modifier: Modifier = Modifier) {
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
fun PlayerDetail(onTeamClick: (Team) -> Unit, player: Player, modifier: Modifier = Modifier) {
    Column (modifier = modifier.padding(horizontal = 8.dp)) {
        ValueWithLabel(R.string.name_label, "${player.firstName} ${player.lastName}")
        ValueWithLabel(R.string.position_label, player.position)
        ValueWithLabel(R.string.height_label, player.height)
        ValueWithLabel(R.string.weight_label, player.weight)
        ValueWithLabel(R.string.jersey_number_label, player.jerseyNumber)
        ValueWithLabel(R.string.college_label, player.college)
        ValueWithLabel(R.string.country_label, player.country)

        if (player.draftYear != null) {
            ValueWithLabel(R.string.draft_year_label, player.draftYear.toString())
        }

        if (player.draftRound != null) {
            ValueWithLabel(R.string.draft_round_label, player.draftRound.toString())
        }

        if (player.draftNumber != null) {
            ValueWithLabel(R.string.draft_number_label, player.draftNumber.toString())
        }

        TeamShort(onTeamClick, player.team)
    }
}

@Composable
fun TeamShort(onTeamClick: (Team) -> Unit, team: Team, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp).clickable { onTeamClick(team) }) {
        SimpleLabel(R.string.team_label)
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                team.name,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = stringResource(R.string.team_detail),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamShortPreview(modifier: Modifier = Modifier) {
    TeamShort({}, Team(1, "", "", "", "Thunder", "", ""))
}