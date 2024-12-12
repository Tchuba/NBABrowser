package com.example.nbabrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbabrowser.data.NBARepository
import com.example.nbabrowser.model.Team
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface TeamUiState {
    data class Success(val team: Team): TeamUiState
    object Error: TeamUiState
    object Loading: TeamUiState
}

class TeamViewModel(
    savedStateHandle: SavedStateHandle,
    private val nbaRepository: NBARepository
): ViewModel() {
    var uiState: TeamUiState by mutableStateOf(TeamUiState.Loading)
        private set

    private val teamId: Int = checkNotNull(savedStateHandle[TeamDestination.TEAM_ID_ARG])

    init {
        getTeam()
    }

    private fun getTeam() {
        viewModelScope.launch {
            uiState = TeamUiState.Loading
            uiState = try {
                val team = nbaRepository.getTeam(teamId)
                TeamUiState.Success(team)
            } catch (e: IOException) {
                TeamUiState.Error
            } catch (e: HttpException) {
                TeamUiState.Error
            }
        }
    }
}