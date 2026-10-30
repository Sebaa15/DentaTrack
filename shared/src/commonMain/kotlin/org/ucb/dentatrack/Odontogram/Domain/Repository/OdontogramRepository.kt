package org.ucb.dentatrack.Odontogram.Domain.Repository

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth

interface OdontogramRepository{
    suspend fun getTeeth(userId: String): List<Tooth>
    suspend fun subirOdontogramaDePrueba(userId: String): Int
    suspend fun descargarOdontogramaDePrueba(
        userId: String
    ): RestoreSummary
    suspend fun saveTooth(
        userId: String,
        tooth: Tooth
    )
}
data class RestoreSummary(
    val remoteCount: Int,
    val insertedCount: Int
)