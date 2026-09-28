package org.ucb.dentatrack.Crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDto(
    val name: String = "",
    val symbol: String = "",
    val image: String = "",
    @SerialName("current_price")
    val currentPrice: Double = 0.0,
    @SerialName("price_change_percentage_24h")
    val priceChange24h: Double? = null,
    @SerialName("market_cap")
    val marketCap: Long = 0,
    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null
)