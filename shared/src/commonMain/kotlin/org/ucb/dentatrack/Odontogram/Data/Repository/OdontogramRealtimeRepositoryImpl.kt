package org.ucb.dentatrack.Odontogram.Data.Repository

import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramRealtimeDataSource
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRealtimeRepository

class OdontogramRealtimeRepositoryImpl(
    private val realtimeDataSource:
    OdontogramRealtimeDataSource
) : OdontogramRealtimeRepository {

    override suspend fun observe():
            Flow<String?> {

        return realtimeDataSource
            .observeMessage()
    }
}