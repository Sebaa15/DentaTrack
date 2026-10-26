package org.ucb.dentatrack.Odontogram.Domain.UseCase


import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class SyncOdontogramUseCase(
    private val repository: OdontogramRepository
) {
    suspend operator fun invoke(userId: String): Int {
        return repository.subirOdontogramaDePrueba(userId)
    }
}