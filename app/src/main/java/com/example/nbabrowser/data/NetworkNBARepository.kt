package com.example.nbabrowser.data

import com.example.nbabrowser.model.Player
import com.example.nbabrowser.model.Team
import com.example.nbabrowser.network.NBAApiService

class NetworkNBARepository(
    private val nbaApiService: NBAApiService
): NBARepository {
    override suspend fun getPlayers(cursor: Int): List<Player> {
        val response = nbaApiService.getPlayers(cursor)

        return response.data
    }

    override suspend fun getPlayer(id: Int): Player {
        val response = nbaApiService.getPlayer(id)

        return response.data
    }

    override suspend fun getTeam(id: Int): Team {
        val response = nbaApiService.getTeam(id)

        return response.data
    }
}