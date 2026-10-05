package org.ucb.dentatrack.ExchangeRate.Data.repository

import org.ucb.dentatrack.ExchangeRate.Data.datasource.ExchangeRateLocalDataSource
import org.ucb.dentatrack.ExchangeRate.Data.mapper.toEntity
import org.ucb.dentatrack.ExchangeRate.Data.mapper.toModel
import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate
import org.ucb.dentatrack.ExchangeRate.Domain.repository.ExchangeRateRepository


class ExchangeRateRepositoryImpl(
    private val localDataSource: ExchangeRateLocalDataSource,
): ExchangeRateRepository {
    override suspend fun getAll(): List<ExchangeRate> {
        return localDataSource.getAll().map { it.toModel() }
    }
    override suspend fun save(
        exchangeRate: ExchangeRate
    ) {
        localDataSource.save(exchangeRate.toEntity())
    }
}