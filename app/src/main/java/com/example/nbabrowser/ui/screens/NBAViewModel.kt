package com.example.nbabrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nbabrowser.NBABrowserApplication
import com.example.nbabrowser.data.NBARepository
import com.example.nbabrowser.model.Player
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface NBAUiState {
    data class Success(val players: List<Player>): NBAUiState
    object Error: NBAUiState
    object Loading: NBAUiState
}

class NBAViewModel(private val nbaRepository: NBARepository): ViewModel() {
    var nbaUiState: NBAUiState by mutableStateOf(NBAUiState.Loading)
        private set

    init {
        getPlayers()
    }

    fun getPlayers() {
        viewModelScope.launch {
            nbaUiState = NBAUiState.Loading
            nbaUiState = try {
                val players = nbaRepository.getPlayers()
                NBAUiState.Success(players)
            } catch (e: IOException) {
                NBAUiState.Error
            } catch (e: HttpException) {
                NBAUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NBABrowserApplication)
                val nbaRepository = application.container.nbaRepository
                NBAViewModel(nbaRepository = nbaRepository)
            }
        }
    }
}