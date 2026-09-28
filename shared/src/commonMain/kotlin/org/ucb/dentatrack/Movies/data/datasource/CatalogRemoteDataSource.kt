package org.ucb.dentatrack.Movies.data.datasource

import org.ucb.dentatrack.Movies.domain.model.MovieModel

interface CatalogRemoteDataSource{
    suspend fun fetchData():Result<List<MovieModel>>
}