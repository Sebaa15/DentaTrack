package org.ucb.dentatrack.ExchangeRate.Domain.usecase

import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate
import org.ucb.dentatrack.ExchangeRate.Domain.repository.ExchangeRateRepository

class SaveExchangeRateUseCase(
    private val repository: ExchangeRateRepository
){
    suspend operator fun invoke(
        value:Double
    ){
        repository.save(ExchangeRate(value = value))
    }
}