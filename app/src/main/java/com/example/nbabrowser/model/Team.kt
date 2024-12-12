package com.example.nbabrowser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    @SerialName(value = "full_name")
    val fullName: String,
    val abbreviation: String,
)
