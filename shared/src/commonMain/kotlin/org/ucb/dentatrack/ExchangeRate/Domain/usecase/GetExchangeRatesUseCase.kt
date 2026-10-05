package org.ucb.dentatrack.ExchangeRate.Domain.usecase

import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate
import org.ucb.dentatrack.ExchangeRate.Domain.repository.ExchangeRateRepository

class GetExchangeRatesUseCase(
    private val repository: ExchangeRateRepository
){
    suspend operator fun invoke(): List<ExchangeRate>{
        return repository.getAll()
    }
}
