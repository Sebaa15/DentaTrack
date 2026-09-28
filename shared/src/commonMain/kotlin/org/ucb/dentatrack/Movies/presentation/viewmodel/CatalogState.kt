package org.ucb.dentatrack.Movies.presentation.viewmodel

import org.ucb.dentatrack.Movies.domain.model.MovieModel

data class CatalogState(
    val movies: List<MovieModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)