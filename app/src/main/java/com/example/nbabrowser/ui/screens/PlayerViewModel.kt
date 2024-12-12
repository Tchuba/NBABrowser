package com.example.nbabrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbabrowser.data.NBARepository
import com.example.nbabrowser.model.Player
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PlayerUiState {
    data class Success(val player: Player): PlayerUiState
    object Error: PlayerUiState
    object Loading: PlayerUiState
}

class PlayerViewModel(
    savedStateHandle: SavedStateHandle,
    private val nbaRepository: NBARepository
): ViewModel() {
    var uiState: PlayerUiState by mutableStateOf(PlayerUiState.Loading)
        private set

    private val playerId: Int = checkNotNull(savedStateHandle[PlayerDestination.playerIdArg])

    init {
        getPlayer()
    }

    fun getPlayer() {
        viewModelScope.launch {
            uiState = PlayerUiState.Loading
            uiState = try {
                val player = nbaRepository.getPlayer(playerId)
                PlayerUiState.Success(player)
            } catch (e: IOException) {
                PlayerUiState.Error
            } catch (e: HttpException) {
                PlayerUiState.Error
            }
        }
    }
}