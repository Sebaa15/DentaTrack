package org.ucb.dentatrack.Movies.data.mapper

import org.ucb.dentatrack.Movies.data.dto.MovieDto
import org.ucb.dentatrack.Movies.domain.model.MovieModel

fun MovieDto.toModel(): MovieModel {
    return MovieModel(
        title=title,
        posterPath=posterPath?:"")
}
