package org.ucb.dentatrack.Crypto.domain.usecase

import org.ucb.dentatrack.Crypto.domain.model.CryptoModel
import org.ucb.dentatrack.Crypto.domain.repository.CryptoRepository

class GetCryptoUseCase(
    private val repository: CryptoRepository
) {
    suspend fun invoke(): Result<List<CryptoModel>> {
        return repository.getCrypto()
    }
}