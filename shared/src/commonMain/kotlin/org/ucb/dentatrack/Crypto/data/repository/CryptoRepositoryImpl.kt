package org.ucb.dentatrack.Crypto.data.repository

import org.ucb.dentatrack.Crypto.data.dataSource.CryptoRemoteDataSource
import org.ucb.dentatrack.Crypto.domain.model.CryptoModel
import org.ucb.dentatrack.Crypto.domain.repository.CryptoRepository

class CryptoRepositoryImpl(
    private val dataSource: CryptoRemoteDataSource
) : CryptoRepository {

    override suspend fun getCrypto(): Result<List<CryptoModel>> {
        return dataSource.fetchData()
    }
}