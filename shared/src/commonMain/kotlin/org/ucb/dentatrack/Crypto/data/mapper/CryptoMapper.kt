package org.ucb.dentatrack.Crypto.data.mapper

import org.ucb.dentatrack.Crypto.data.dto.CryptoDto
import org.ucb.dentatrack.Crypto.domain.model.CryptoModel

fun CryptoDto.toModel() = CryptoModel(
    name = name,
    symbol = symbol,
    image = image,
    currentPrice = currentPrice,
    priceChange24h = priceChange24h ?: 0.0,
    marketCap = marketCap,
    marketCapRank = marketCapRank ?: 0
)