package com.example.nbabrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbabrowser.data.NBARepository
import com.example.nbabrowser.model.Player
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val players: List<Player>): HomeUiState
    object Error: HomeUiState
    object Loading: HomeUiState
}

class HomeViewModel(private val nbaRepository: NBARepository): ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPlayers()
    }

    fun getPlayers() {
        viewModelScope.launch {
            uiState = HomeUiState.Loading
            uiState = try {
                val players = nbaRepository.getPlayers()
                HomeUiState.Success(players)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}