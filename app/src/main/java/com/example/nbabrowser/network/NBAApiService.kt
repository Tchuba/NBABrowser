package com.example.nbabrowser.network

import com.example.nbabrowser.model.PlayerResponse
import com.example.nbabrowser.model.PlayersResponse
import com.example.nbabrowser.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PAGE_SIZE = 35

interface NBAApiService {
    @GET("players?per_page=${PAGE_SIZE}")
    suspend fun getPlayers(@Query("cursor") cursor: Int = 0): PlayersResponse

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): PlayerResponse

    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: Int = 0): TeamResponse
}
