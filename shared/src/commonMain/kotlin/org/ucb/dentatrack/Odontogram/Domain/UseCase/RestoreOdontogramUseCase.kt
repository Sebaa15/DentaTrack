package org.ucb.dentatrack.Odontogram.Domain.UseCase

import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.Odontogram.Domain.Repository.RestoreSummary

class RestoreOdontogramUseCase(
    private val repository: OdontogramRepository
) {
    suspend operator fun invoke(
        userId: String
    ): RestoreSummary {
        return repository.descargarOdontogramaDePrueba(userId)
    }
}
