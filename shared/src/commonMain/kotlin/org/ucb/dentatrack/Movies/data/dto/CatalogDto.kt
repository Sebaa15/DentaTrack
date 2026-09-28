package org.ucb.dentatrack.Movies.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    val page: Int=0,
    val results:List<MovieDto> = emptyList()
)