package com.example.nbabrowser.network

import com.example.nbabrowser.model.PlayersResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val PAGE_SIZE = 35

interface NBAApiService {
    @GET("players?per_page=${PAGE_SIZE}")
    suspend fun getPlayers(@Query("cursor") cursor: Int = 0): PlayersResponse
}
