package com.example.nbabrowser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Player(
    val id: Int,
    @SerialName(value = "first_name")
    val firstName: String,
    @SerialName(value = "last_name")
    val lastName: String,
    val position: String,
    val height: String,
    val weight: String,
    @SerialName(value = "jersey_number")
    val jerseyNumber: String,
    val college: String,
    val country: String,
    @SerialName(value = "draft_year")
    val draftYear: Int?,
    @SerialName(value = "draft_round")
    val draftRound: Int?,
    @SerialName(value = "draft_number")
    val draftNumber: Int?,
    val team: Team,
    @Transient
    val photo: String = "https://variety.com/wp-content/uploads/2021/07/Rick-Astley-Never-Gonna-Give-You-Up.png"
)
