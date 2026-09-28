package org.ucb.dentatrack.Movies.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val title: String="",
    @SerialName("poster_path")
    val posterPath: String?=null
)