package org.ucb.dentatrack.Movies.data.repository

import org.ucb.dentatrack.Movies.data.datasource.CatalogRemoteDataSource
import org.ucb.dentatrack.Movies.domain.model.MovieModel
import org.ucb.dentatrack.Movies.domain.repository.CatalogRepository


class CatalogRepositoryImpl(
    private val dataSource: CatalogRemoteDataSource
): CatalogRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return dataSource.fetchData()
    }
}