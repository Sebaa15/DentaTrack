package org.ucb.dentatrack.Crypto.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ucb.dentatrack.Crypto.data.dataSource.CryptoRemoteDataSource
import org.ucb.dentatrack.Crypto.data.dto.CryptoDto
import org.ucb.dentatrack.Crypto.data.mapper.toModel
import org.ucb.dentatrack.Crypto.domain.model.CryptoModel

class CryptoService : CryptoRemoteDataSource {
    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    override suspend fun fetchData(): Result<List<CryptoModel>> {
        return try {
            val response = client.get("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd")
                .body<List<CryptoDto>>()

            Result.success(response.map { it.toModel() })

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}