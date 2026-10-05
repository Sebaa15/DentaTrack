package org.ucb.dentatrack.Odontogram.Data.Repository

import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSource
import org.ucb.dentatrack.Odontogram.Data.Mapper.toDomain
import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class OdontogramRepositoryImpl(
    private val localDataSource:
    OdontogramLocalDataSource
) : OdontogramRepository {

    override suspend fun getTeeth():
            List<Tooth> {

        return localDataSource
            .getTeeth()
            .map {
                it.toDomain()
            }
    }
}