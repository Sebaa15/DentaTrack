package org.ucb.dentatrack.Crypto.domain.repository


import org.ucb.dentatrack.Crypto.domain.model.CryptoModel

interface CryptoRepository {
    suspend fun getCrypto(): Result<List<CryptoModel>>
}