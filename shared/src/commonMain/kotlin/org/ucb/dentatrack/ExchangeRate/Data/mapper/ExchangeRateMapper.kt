package org.ucb.dentatrack.ExchangeRate.Data.mapper

import org.ucb.dentatrack.ExchangeRate.Data.entity.ExchangeRateEntity
import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate

fun ExchangeRateEntity.toModel(): ExchangeRate {
    return ExchangeRate(
        id = id,
        value = value
    )
}
fun ExchangeRate.toEntity(): ExchangeRateEntity {
    return ExchangeRateEntity(
        id = id,
        value = value
    )
}