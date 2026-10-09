package org.ucb.dentatrack.Odontogram.Domain.Repository

import kotlinx.coroutines.flow.Flow

interface OdontogramRealtimeRepository {

    suspend fun observe():
            Flow<String?>
}