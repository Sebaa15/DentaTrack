package org.ucb.dentatrack.Odontogram.Data.DataSource

import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

interface OdontogramLocalDataSource {

    suspend fun getTeeth():
            List<ToothEntity>

    suspend fun saveTeeth(
        teeth: List<ToothEntity>
    )
}