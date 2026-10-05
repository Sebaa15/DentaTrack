package org.ucb.dentatrack.ExchangeRate.Data.datasource

import org.ucb.dentatrack.ExchangeRate.Data.dao.ExchangeRateDao
import org.ucb.dentatrack.ExchangeRate.Data.entity.ExchangeRateEntity

class ExchangeRateLocalDataSourceImpl(private val dao:ExchangeRateDao) :
    ExchangeRateLocalDataSource {
    override suspend fun getAll(): List<ExchangeRateEntity> {
        return dao.getAll()
    }
    override suspend fun save(
        exchangeRate:ExchangeRateEntity
    ){
        dao.insert(exchangeRate)
    }
}