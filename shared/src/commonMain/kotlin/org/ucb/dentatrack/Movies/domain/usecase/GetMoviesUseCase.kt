package org.ucb.dentatrack.Movies.domain.usecase

import org.ucb.dentatrack.Movies.domain.model.MovieModel
import org.ucb.dentatrack.Movies.domain.repository.CatalogRepository

class GetMoviesUseCase(
    private val catalogRepository: CatalogRepository
){
    suspend fun invoke(): Result<List<MovieModel>>{
        return catalogRepository.getMovies()
    }
}