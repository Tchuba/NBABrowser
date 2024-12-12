package com.example.nbabrowser.data

import com.example.nbabrowser.model.Player

interface NBARepository {
    suspend fun getPlayers(cursor: Int = 0): List<Player>
}