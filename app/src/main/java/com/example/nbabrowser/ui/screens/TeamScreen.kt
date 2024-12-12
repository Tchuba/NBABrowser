package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Team
import com.example.nbabrowser.ui.AppViewModelProvider
import com.example.nbabrowser.ui.TopAppBar
import com.example.nbabrowser.ui.navigation.NavigationDestination
import com.example.nbabrowser.ui.shared.ErrorScreen
import com.example.nbabrowser.ui.shared.LoadingScreen
import com.example.nbabrowser.ui.shared.ValueWithLabel

object TeamDestination : NavigationDestination {
    override val route: String = "team_detail"
    const val TEAM_ID_ARG = "teamId"
    val routeWithArgs = "$route/{$TEAM_ID_ARG}"
    override val titleRes: Int = R.string.team_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeamViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = TeamDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TeamBody(
                uiState = uiState, modifier = Modifier.padding(
                    top = it.calculateTopPadding()
                )
            )
        }
    }
}

@Composable
fun TeamBody(uiState: TeamUiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        when (uiState) {
            is TeamUiState.Loading -> LoadingScreen()
            is TeamUiState.Success -> TeamDetail(uiState.team)
            is TeamUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun TeamDetail(player: Team, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.horizontal_padding))
            .verticalScroll(rememberScrollState())
            .padding(bottom = dimensionResource(R.dimen.bottom_padding))
    ) {
        ValueWithLabel(R.string.name_label, player.name)
        ValueWithLabel(R.string.full_name_label, player.fullName)
        ValueWithLabel(R.string.abbreviation_label, player.abbreviation)
        ValueWithLabel(R.string.conference_label, player.conference)
        ValueWithLabel(R.string.division_label, player.division)
        ValueWithLabel(R.string.city_label, player.city)
    }
}