package com.example.nbabrowser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponse(
    val data: List<Player>,
    val meta: MetaData
)

@Serializable
data class PlayerResponse(
    val data: Player
)

@Serializable
data class TeamResponse(
    val data: Team
)

@Serializable
data class MetaData(
    @SerialName(value = "prev_cursor")
    val prevCursor: Int? = null,
    @SerialName(value = "next_cursor")
    val nextCursor: Int,
    @SerialName(value = "per_page")
    val perPage: Int
)