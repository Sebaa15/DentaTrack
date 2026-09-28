package org.ucb.dentatrack.Crypto.domain.model
data class CryptoModel(
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val priceChange24h: Double,
    val marketCap: Long,
    val marketCapRank: Int
)