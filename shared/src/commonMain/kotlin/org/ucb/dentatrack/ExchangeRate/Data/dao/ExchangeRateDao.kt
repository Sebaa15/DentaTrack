package org.ucb.dentatrack.ExchangeRate.Data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import org.ucb.dentatrack.ExchangeRate.Data.entity.ExchangeRateEntity

@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM exchange_rates ORDER BY id DESC")
    suspend fun getAll(): List<ExchangeRateEntity>

    @Insert
    suspend fun insert(
        exchangeRate: ExchangeRateEntity
    )
}