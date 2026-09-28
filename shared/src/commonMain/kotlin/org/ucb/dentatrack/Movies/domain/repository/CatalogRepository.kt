package org.ucb.dentatrack.Movies.domain.repository

import org.ucb.dentatrack.Movies.domain.model.MovieModel

interface  CatalogRepository{
    suspend fun getMovies(): Result<List<MovieModel>>
}