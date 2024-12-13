package com.example.nbabrowser.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nbabrowser.NBABrowserApplication
import com.example.nbabrowser.ui.screens.HomeViewModel
import com.example.nbabrowser.ui.screens.PlayerViewModel
import com.example.nbabrowser.ui.screens.TeamViewModel

/**
 * View model initialization through a shared factory, binding the necessary repository.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(nbaApplication().container.nbaRepository)
        }
        initializer {
            PlayerViewModel(this.createSavedStateHandle(), nbaApplication().container.nbaRepository)
        }
        initializer {
            TeamViewModel(this.createSavedStateHandle(), nbaApplication().container.nbaRepository)
        }
    }
}

fun CreationExtras.nbaApplication(): NBABrowserApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NBABrowserApplication)