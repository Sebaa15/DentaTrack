package org.ucb.dentatrack.Movies.presentation.viewmodel

sealed interface CatalogEvent {
    data object LoadMovie: CatalogEvent
    data object Retry: CatalogEvent
}