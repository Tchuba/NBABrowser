@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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

object PlayerDestination : NavigationDestination {
    override val route: String = "player_detail"
    const val PLAYER_ID_ARG = "playerId"
    val routeWithArgs = "$route/{$PLAYER_ID_ARG}"
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
        topBar = {
            TopAppBar(
                title = PlayerDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            PlayerBody(
                onTeamClick = { navigateToTeam(it.id) },
                uiState = uiState,
                modifier = Modifier.padding(
                    top = it.calculateTopPadding()
                )
            )
        }
    }
}

@Composable
fun PlayerBody(onTeamClick: (Team) -> Unit, uiState: PlayerUiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        when (uiState) {
            is PlayerUiState.Loading -> LoadingScreen()
            is PlayerUiState.Success -> PlayerDetail(
                onTeamClick = onTeamClick,
                player = uiState.player
            )

            is PlayerUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun PlayerDetail(onTeamClick: (Team) -> Unit, player: Player, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.horizontal_padding))
            .verticalScroll(rememberScrollState())
            .padding(bottom = dimensionResource(R.dimen.bottom_padding))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = player.photo,
                contentDescription = stringResource(R.string.player_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.large_photo_size))
                    .clip(CircleShape),
                alignment = Alignment.Center
            )

            Text(
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.medium_padding)),
                text = "${player.firstName} ${player.lastName}",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Column {
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

}

@Composable
fun TeamShort(onTeamClick: (Team) -> Unit, team: Team, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.medium_padding))
            .clickable { onTeamClick(team) }) {
        SimpleLabel(R.string.team_label)
        Row(
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