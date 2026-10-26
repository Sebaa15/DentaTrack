package org.ucb.dentatrack.Odontogram.Domain.UseCase

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class GetOdontogramUseCase(
    private val repository: OdontogramRepository
){
    suspend operator fun invoke(userId: String): List<Tooth> {
        return repository.getTeeth(userId)
    }
}