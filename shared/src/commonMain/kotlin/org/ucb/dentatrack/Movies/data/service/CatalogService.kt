package org.ucb.dentatrack.Movies.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ucb.dentatrack.Movies.data.datasource.CatalogRemoteDataSource
import org.ucb.dentatrack.Movies.data.dto.CatalogDto
import org.ucb.dentatrack.Movies.data.dto.MovieDto
import org.ucb.dentatrack.Movies.data.mapper.toModel
import org.ucb.dentatrack.Movies.domain.model.MovieModel

class CatalogService: CatalogRemoteDataSource {
    private val client= HttpClient {
        expectSuccess=true
        install(ContentNegotiation){
                json(
                    Json{
                        prettyPrint=true
                        isLenient=true
                        ignoreUnknownKeys=true
                    }
                )
            }
        }


    override suspend fun fetchData(): Result<List<MovieModel>> {

        return try {

            val response = client.get(
                "$BASE_URL/discover/movie"
            ) {
                parameter(
                    "sort_by",
                    "popularity.desc"
                )

                parameter(
                    "api_key",
                    API_KEY
                )
            }.body<CatalogDto>()

            Result.success(
                response.results.map {
                    it.toModel()
                }
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    companion object {
        const val BASE_URL =
            "https://api.themoviedb.org/3"

        const val API_KEY =
            "fa3e844ce31744388e07fa47c7c5d8c3"
    }

}