package org.ucb.dentatrack.Crypto.data.dataSource


import org.ucb.dentatrack.Crypto.domain.model.CryptoModel

interface CryptoRemoteDataSource {
    suspend fun fetchData(): Result<List<CryptoModel>>
}