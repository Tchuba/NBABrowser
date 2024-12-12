package com.example.nbabrowser.data

import com.example.nbabrowser.model.Player
import com.example.nbabrowser.model.PlayersResponse
import com.example.nbabrowser.model.Team

interface NBARepository {
    suspend fun getPlayers(cursor: Int = 0): PlayersResponse
    suspend fun getPlayer(id: Int): Player
    suspend fun getTeam(id: Int): Team
}