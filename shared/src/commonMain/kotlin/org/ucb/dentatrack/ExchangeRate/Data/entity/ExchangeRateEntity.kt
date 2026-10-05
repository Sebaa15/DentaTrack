package org.ucb.dentatrack.ExchangeRate.Data.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: Double
)