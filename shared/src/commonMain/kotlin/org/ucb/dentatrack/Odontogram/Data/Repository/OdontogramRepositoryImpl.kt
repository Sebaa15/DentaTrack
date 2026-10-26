package org.ucb.dentatrack.Odontogram.Data.Repository

import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSource
import org.ucb.dentatrack.Odontogram.Data.Mapper.toDomain
import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramFirestoreDataSource
import org.ucb.dentatrack.Odontogram.Data.Dto.ToothFirestoreDto
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity
import org.ucb.dentatrack.Odontogram.Domain.Repository.RestoreSummary

class OdontogramRepositoryImpl(
    private val localDataSource: OdontogramLocalDataSource,
    private val firestoreDataSource: OdontogramFirestoreDataSource
) : OdontogramRepository {

    override suspend fun getTeeth(userId: String): List<Tooth> {

        val localId = userId.toInt()

        return localDataSource
            .getTeeth(localId)
            .map { it.toDomain() }
    }
    override suspend fun subirOdontogramaDePrueba(
        userId: String
    ): Int {

        val localId = userId.toIntOrNull()
            ?: error("ID local inválido")

        val dientes = localDataSource.getTeeth(localId)

        val datosRemotos = dientes.map {
            ToothFirestoreDto(
                number = it.number,
                status = it.status,
                treatment = it.treatment
            )
        }

        return firestoreDataSource.subirOdontogramaPrueba(
            localUserId = localId,
            dientes = datosRemotos
        )
    }
    override suspend fun descargarOdontogramaDePrueba(
        userId: String
    ): RestoreSummary {

        val localId = userId.toIntOrNull()
            ?: error("ID local inválido")

        val remotos = firestoreDataSource
            .descargarOdontogramaPrueba(localId)

        val entidades = remotos.map {
            ToothEntity(
                userId = localId,
                number = it.number,
                status = it.status,
                treatment = it.treatment
            )
        }

        val insertados = localDataSource
            .insertMissingTeeth(entidades)

        return RestoreSummary(
            remoteCount = remotos.size,
            insertedCount = insertados
        )
    }
}