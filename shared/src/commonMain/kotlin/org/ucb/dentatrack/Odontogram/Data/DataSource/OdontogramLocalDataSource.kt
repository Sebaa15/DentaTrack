package org.ucb.dentatrack.Odontogram.Data.DataSource

import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

interface OdontogramLocalDataSource {

    suspend fun getTeeth(userId: Int): List<ToothEntity>
    suspend fun saveTeeth(
        teeth: List<ToothEntity>
    )
    suspend fun insertMissingTeeth(
        teeth: List<ToothEntity>
    ): Int
}