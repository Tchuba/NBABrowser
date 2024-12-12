package com.example.nbabrowser.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nbabrowser.data.NBARepository
import com.example.nbabrowser.data.PlayerPagingSource
import com.example.nbabrowser.network.PAGE_SIZE

class HomeViewModel(private val nbaRepository: NBARepository): ViewModel() {
    val playersPagedData = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 10,
            enablePlaceholders = false
        )) {
            PlayerPagingSource(nbaRepository)
        }.flow.cachedIn(viewModelScope)
}
