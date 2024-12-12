package com.example.nbabrowser.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
            PlayerBody(uiState = uiState, Modifier.fillMaxSize().padding(it))
        }
    }
}

@Composable
fun PlayerBody(uiState: PlayerUiState, modifier: Modifier = Modifier) {
    when (uiState) {
        is PlayerUiState.Loading -> LoadingScreen(modifier = modifier)
        is PlayerUiState.Success -> PlayerDetail(
            uiState.player, modifier = modifier
        )
        is PlayerUiState.Error -> ErrorScreen( modifier = modifier)
    }
}

@Composable
fun PlayerDetail(player: Player, modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        ValueWithLabel(R.string.position_label, player.position)
    }
}

@Composable
fun ValueWithLabel(@StringRes title: Int, text: String) {
    Text(stringResource(title))
    Text(text)
}