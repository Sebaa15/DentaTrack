package org.ucb.dentatrack.ExchangeRate.Data.datasource


import org.ucb.dentatrack.ExchangeRate.Data.entity.ExchangeRateEntity

interface ExchangeRateLocalDataSource {

    suspend fun getAll(): List<ExchangeRateEntity>

    suspend fun save(
        exchangeRate: ExchangeRateEntity
    )
}