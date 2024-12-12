package com.example.nbabrowser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nbabrowser.R
import com.example.nbabrowser.model.Team
import com.example.nbabrowser.ui.AppViewModelProvider
import com.example.nbabrowser.ui.TopAppBar
import com.example.nbabrowser.ui.navigation.NavigationDestination
import com.example.nbabrowser.ui.shared.ErrorScreen
import com.example.nbabrowser.ui.shared.LoadingScreen
import com.example.nbabrowser.ui.shared.ValueWithLabel

object TeamDestination: NavigationDestination {
    override val route: String = "team_detail"
    const val teamIdArg = "teamId"
    val routeWithArgs = "$route/{$teamIdArg}"
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
        topBar = { TopAppBar(
            title = TeamDestination.titleRes,
            canNavigateBack = true,
            navigateUp = onNavigateUp
        ) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TeamBody(uiState = uiState, Modifier.fillMaxSize().padding(it))
        }
    }
}

@Composable
fun TeamBody(uiState: TeamUiState, modifier: Modifier = Modifier) {
    when (uiState) {
        is TeamUiState.Loading -> LoadingScreen(modifier = modifier)
        is TeamUiState.Success -> TeamDetail(
            uiState.team, modifier = modifier
        )
        is TeamUiState.Error -> ErrorScreen( modifier = modifier)
    }
}

@Composable
fun TeamDetail(player: Team, modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        ValueWithLabel(R.string.name_label, player.name)
    }
}