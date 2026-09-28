package org.ucb.dentatrack.Movies.presentation.viewmodel

sealed interface CatalogEffect{
    data class ShowMessage(
        val message: String
    ): CatalogEffect
}