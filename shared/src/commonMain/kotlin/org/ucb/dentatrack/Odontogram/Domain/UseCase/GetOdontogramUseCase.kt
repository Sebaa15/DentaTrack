package org.ucb.dentatrack.Odontogram.Domain.UseCase

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class GetOdontogramUseCase(
    private val repository: OdontogramRepository
){
    operator fun invoke():List<Tooth>{
        return repository.getTeeth()
    }
}