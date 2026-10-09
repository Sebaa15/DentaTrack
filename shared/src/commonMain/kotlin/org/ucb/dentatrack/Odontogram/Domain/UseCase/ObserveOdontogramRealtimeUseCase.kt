package org.ucb.dentatrack.Odontogram.Domain.UseCase

import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRealtimeRepository

class ObserveOdontogramRealtimeUseCase(
    private val repository:
    OdontogramRealtimeRepository
) {

    suspend operator fun invoke():
            Flow<String?> {

        return repository.observe()
    }
}