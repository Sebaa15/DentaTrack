package org.ucb.dentatrack.ExchangeRate.Domain.repository

import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate

interface ExchangeRateRepository{
    suspend fun getAll(): List<ExchangeRate>
    suspend fun save(
        exchangeRate: ExchangeRate
    )
}
